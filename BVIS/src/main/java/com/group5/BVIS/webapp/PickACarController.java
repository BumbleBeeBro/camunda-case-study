package com.group5.BVIS.webapp;


import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.Cars.Car;
import com.group5.BVIS.Cars.CarRepository;
import com.group5.BVIS.webapp.formModels.PickACar;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

/**
 * Controller for PickACar View, uses the Car WS to retrieve models to choose from.
 */
@Controller
public class PickACarController {
	
	//autowire repository and runtime service.
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private RuntimeService runtimeService;
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	/**
	 * Controller for PickACar View. Handles get request.
	 * @param processInstanceId, String. Process instance ID as unique identifier.
	 * @param model, Model. Page Model.
	 * @return PickACar View.
	 */
    @GetMapping("/{processInstanceId}/pick-a-car")
    public String customerInfo(@PathVariable("processInstanceId") String processInstanceId, Model model){
    	
    	//restcall to retrieve all cars
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<List<Car>> r = restTemplate.exchange("http://localhost:8080/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>(){});
    	
    	//add cars to model.
    	model.addAttribute("cars", r.getBody());
    	model.addAttribute("processInstanceId", processInstanceId);
        
    	//returns view.
    	return "pick-a-car";
    }
    
    /**
     * Controller for PickACar View. Handles post request.
     * @param processInstanceId, String. Process instance ID as unique identifier.
     * @param pickACar, PickACar. View.
     * @param bindingResult, BindingResult. Used for validation.
     * @param model, Model. Page model.
     * @return next View.
     */
    @PostMapping("/{processInstanceId}/pick-a-car")
    public String customerInfoSubmit(@PathVariable("processInstanceId") String processInstanceId, @Valid PickACar pickACar, BindingResult bindingResult, Model model) {
    	
    	//errorhandling
    	if (bindingResult.hasErrors()) {
    		ArrayList<String> errors = new ArrayList<String>();
    		for(ObjectError error : bindingResult.getAllErrors()) {
    			errors.add(error.getDefaultMessage());
    		}
    		model.addAttribute("errors", errors);
            return "pick-a-car";
        }
    	
    	//set selected cars.
    	runtimeService.setVariable(processInstanceId, "carId", pickACar.getId());
    	
    	runtimeService.setVariable(processInstanceId, "number_of_vehicles", 1);
    	
    	LOGGER.info(carRepository.findById(pickACar.getId()).toString());
    	
    	//redirects to next View.
		return "redirect:/" + processInstanceId + "/customer-info";
    }
    

}