package com.group5.BVIS.webapp;


import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.Cars.Car;
import com.group5.BVIS.Cars.CarRepository;
import com.group5.BVIS.Contracts.Contract;
import com.group5.BVIS.Contracts.ContractRepository;
import com.group5.BVIS.RestModels.InsuranceOffering;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Controller for contract View, contract confirmation and decline.
 */
@Controller
public class ContractWebController {
	
	//autowire runtime service and necessary repositories
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private ContractRepository contractRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * Controller for contract View. Handles get request. Used to display contract.
	 * @param processInstanceId, String. 
	 * @param model, Model. Page model.
	 * @return View.
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/{processInstanceId}/contract")
    public String ShowContract(@PathVariable("processInstanceId") String processInstanceId, Model model){

		Map<String, Object> map = runtimeService.getVariables(processInstanceId, Arrays.asList("customerType", "name", "address", "carId", "number_of_vehicles", "pickupLocation", "returnLocation", "pickupDate", "returnDate", "offerings", "insuranceId"));
		
		//create contract with process variables
		Contract contract = new Contract(processInstanceId,
				(String) map.get("customerType"), 
				(String) map.get("name"), 
				(String) map.get("address"), 
				(Car) carRepository.getOne((Long) map.get("carId")),
				(Long) new Long( (int) map.get("number_of_vehicles")), 
				(String) map.get("pickupLocation"),
				(String) map.get("returnLocation"),
				(Date) map.get("pickupDate"),
				(Date) map.get("returnDate"),
				
				//We subtract 1 from the insurance ID to align it with its position. For example, the first insurance offer hhas the ID 1, but the position 0 in the list.
				((List<InsuranceOffering>) map.get("offerings")).get((int) map.get("insuranceId") - 1).getName(),
				 carRepository.getOne((Long) map.get("carId")).getPricePerDay(),
				((List<InsuranceOffering>) map.get("offerings")).get((int) map.get("insuranceId") - 1).getPrice()
		);
		
		//store contract.
		Long Id = restTemplate.postForObject("http://localhost:8080/contracts/create", contract, Long.class);
		
		runtimeService.setVariable(processInstanceId, "contractId", Id);
		
		//add contract and car name to model.
		model.addAttribute("contract", restTemplate.getForObject("http://localhost:8080/contracts/" + Id, Contract.class));
		model.addAttribute("name", contract.getCar().getName());
		
		//return contract View.
        return "contract";
    }
    
	/**
	 * Controller for accepting contract. Handles get request.
	 * @param processInstanceId, String. Unique identifier.
	 * @param model, Model. Page model.
	 * @return ContractComplete View.
	 */
    @GetMapping("/{processInstanceId}/contract-accept")
    public String ContractAccept(@PathVariable("processInstanceId") String processInstanceId,  Model model) {
    	
    	//append contract to the instance, because I can't access databases in delegates.
    	runtimeService.setVariable(processInstanceId, "contractText", restTemplate.getForObject("http://localhost:8080/contracts/" + runtimeService.getVariable(processInstanceId, "contractId"), Contract.class).toString());
    	
    	runtimeService.setVariable(processInstanceId, "contractAccepted", true);
    	
    	//returns contract complete view
		return "contract-complete";
    }
    
    /**
     * Controller for declining contract. Handles get request.
     * @param processInstanceId, String. Unique identifier.
     * @param model, Model. Page model.
     * @return Index View
     */
    @GetMapping("/{processInstanceId}/contract-decline")
    public String ContractDecline(@PathVariable("processInstanceId") String processInstanceId, Model model) {
    	  	
    	//delete contract.
    	restTemplate.delete("http://localhost:8080/contracts/" + runtimeService.getVariable(processInstanceId, "contractId"));
    	
    	//remove process variable
    	runtimeService.removeVariable(processInstanceId, "contractId");
    	
    	//delete process instance if customer is not satisfied with the contract and declines it.
    	runtimeService.deleteProcessInstance(processInstanceId, "Customer declined contract");
    	
    	model.addAttribute("error", "We are sorry to not be able to help you with your order!");
    	
    	//return index View
		return "index";
    }
    
    /**
     * Controller for editing contract.
     * @param processInstanceId, String. Process Instance ID. Used as unique identifier.
     * @param model, Model. Page model.
     * @return PickupAndReturn View.
     */
    @GetMapping("/{processInstanceId}/contract-edit")
    public String ContractEdit(@PathVariable("processInstanceId") String processInstanceId,  Model model) {    	
    	
    	//delete current contract
    	contractRepository.deleteById((Long) runtimeService.getVariable(processInstanceId, "contractId"));
    	
    	//remove contract I
    	runtimeService.removeVariable(processInstanceId, "contractId");
    	
    	//delete process instance if customer is not satisfied with the contract
    	runtimeService.deleteProcessInstance(processInstanceId, "Customer wants to edit contract");
    	
    	//redirect
    	return "redirect:/start-private-rent";
    }
    

}