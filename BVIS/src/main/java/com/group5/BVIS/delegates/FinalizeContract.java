package com.group5.BVIS.delegates;

import java.util.Date;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.Cars.Car;
import com.group5.BVIS.Contracts.Contract;
import com.group5.BVIS.RestModels.InsuranceOffering;

/**
 * Implements service task "Finalize contract" in "BVISContracting" BPMN.
 * Reads the input variables and creates a contract.
 */
public class FinalizeContract implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		//get the ID of the car model.
		String carID = Long.toString((Long)execution.getVariable("carId"));
		RestTemplate restTemplate = new RestTemplate();
		Car car = restTemplate.getForObject("http://localhost:8080/cars/"+carID, Car.class);
		
		//create the contract by retrieving the necessary processvariables from the Camunda instance.
		Contract contract = new Contract(
				(String) execution.getProcessInstanceId(),
				(String) execution.getVariable("customerType"), 
				(String) execution.getVariable("name"), 
				(String) execution.getVariable("address"), 
				car,
				(Long) new Long( (int) execution.getVariable("number_of_vehicles")), 
				(String) execution.getVariable("pickupLocation"),
				(String) execution.getVariable("returnLocation"),
				(Date) execution.getVariable("pickupDate"),
				(Date) execution.getVariable("returnDate"),
				//offer id provided by capitol starts at one, arrays start at 0, needs to reduced by one.
				((List<InsuranceOffering>)execution.getVariable("offerings")).get(((int) execution.getVariable("insuranceId")) - 1).getName(),
				(double) execution.getVariable("pricePerDay"),
				((List<InsuranceOffering>) execution.getVariable("offerings")).get(((int) execution.getVariable("insuranceId")) - 1).getPrice()
		);

		//store contract.
		Long contractId = restTemplate.postForObject("http://localhost:8080/contracts/create", contract, Long.class);
		
		//set the contract text as a process variable.
		execution.setVariable("contractText", restTemplate.getForObject("http://localhost:8080/contracts/" + contractId, Contract.class).toString());
    			
		execution.setVariable("contractId", contractId);
		
		
	}

}
