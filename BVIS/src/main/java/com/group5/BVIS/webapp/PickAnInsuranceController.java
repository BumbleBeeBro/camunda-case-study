package com.group5.BVIS.webapp;


import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.webapp.formModels.PickAnInsurance;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.validation.Valid;

/**
 * Controller for PickAnInsurance View.
 */
@Controller
public class PickAnInsuranceController {
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

	//autowire runtime service.
	@Autowired
	private RuntimeService runtimeService;
	
	/**
	 * Handle get request for pick and insurance controller.
	 * @param processInstanceId, String. Process instance ID. Serves as customer ID.
	 * @param model, Model. Page model.
	 * @return PickAnInsurance View.
	 */
	@GetMapping("/{processInstanceId}/pick-an-insurance")
    public String pickAnInsurance(@PathVariable("processInstanceId") String processInstanceId, Model model){
		
		//Wait for insurances a maximum of 50seconds. This is feasible, as Capitol only uses service tasks to create
		//offers.
		int i = 0;
		while(runtimeService.getVariable(processInstanceId, "offerings") == null || i > 10) {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				LOGGER.info("could not wait for insurances" + e.toString());
				
				model.addAttribute("error", "offerings connection crashed, sorry for the inconvenience");
				return "pick-an-insurance";
			}
			i++;
		}
		//Waiting time exceeded. 
		if(i > 10) {
			model.addAttribute("error", "could not find any insurances, sorry for the inconvenience");
			return "pick-an-insurance";
		}
		
		//add the insurance offerings to model.
    	model.addAttribute("offerings", runtimeService.getVariable(processInstanceId, "offerings"));
    	
    	//return PickAnInsurance View.
        return "pick-an-insurance";
    }
    
	/**
	 * Handles the submit user form.
	 * @param processInstanceId, String. Process instance ID as a unique identifier.
	 * @param pickAnInsurance, PickAnInsurance. Related view.
	 * @param bindingResult, BindingResult. Used for validation.
	 * @param model, Model. Page model.
	 * @return returns a redirect to the next view.
	 */
    @SuppressWarnings("unchecked")
	@PostMapping("/{processInstanceId}/pick-an-insurance")
    public String pickAnInsuranceSubmit(@PathVariable("processInstanceId") String processInstanceId, @Valid PickAnInsurance pickAnInsurance, BindingResult bindingResult, Model model) {
    	
    	//errorhandling
    	if (bindingResult.hasErrors()) {
    		ArrayList<String> errors = new ArrayList<String>();
    		for(ObjectError error : bindingResult.getAllErrors()) {
    			errors.add(error.getDefaultMessage());
    		}
    		model.addAttribute("errors", errors);
            return "pick-an-insurance";
        }
    	
    	//add selected offer ID.
    	runtimeService.setVariable(processInstanceId, "insuranceId", pickAnInsurance.getOfferId());
    	
    	//redirects to next view.
		return "redirect:/" + processInstanceId + "/contract";
    }
    
}