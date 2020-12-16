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
import org.springframework.web.bind.annotation.RequestParam;

import com.group5.BVIS.webapp.formModels.CustomerInfo;


import java.util.ArrayList;


import javax.validation.Valid;

/**
 * Controller for CustomerInfo View. 
 */
@Controller
public class CustomerInfoController {
	
	//autowire runtime service.
	@Autowired
	private RuntimeService runtimeService;

	/**
	 * Controller for CustomerInfo View. Handles get request.
	 * @param processInstanceId, String. Process instance ID as unique identifier.
	 * @param model, Model. Page model.
	 * @return CustomerInfo View.
	 */
    @GetMapping("/{processInstanceId}/customer-info")
    public String customerInfo(@PathVariable("processInstanceId") String processInstanceId, Model model){

    	//add process instance ID.
    	model.addAttribute("processInstanceId", processInstanceId);
    	
    	//return view.
        return "customer-info";
    }
    
    /**
     * Controller for CustomerInfo View. Handles the customer input form submitted via post.
     * @param processInstanceId, String. Process instance ID as unique identifier.
     * @param customerInfo, CustomerInfo. View.
     * @param bindingResult, BindingResult. Used for validation.
     * @param model, Model. Page model.
     * @return redirect to next view.
     */
    @PostMapping("/{processInstanceId}/customer-info")
    public String customerInfoSubmit(@PathVariable("processInstanceId") String processInstanceId, @Valid CustomerInfo customerInfo, BindingResult bindingResult, Model model) {
    	
    	//errorhandling
    	if (bindingResult.hasErrors()) {
    		ArrayList<String> errors = new ArrayList<String>();
    		for(ObjectError error : bindingResult.getAllErrors()) {
    			errors.add(error.getDefaultMessage());
    		}
    		model.addAttribute("errors", errors);
            return "customer-info";
        }
    	
    	//add input data as process variables.
    	runtimeService.setVariable(processInstanceId, "name", "" + customerInfo.getFirstName() + " " + customerInfo.getLastName());
    	runtimeService.setVariable(processInstanceId, "birthDate", customerInfo.getBirthDate());
    	
    	runtimeService.setVariable(processInstanceId, "address", customerInfo.getStreetName() + " " + 
																 customerInfo.getHouseNumber() + " " + 
																 customerInfo.getZipCode() + " " + 
																 customerInfo.getCity());
    	
    	runtimeService.setVariable(processInstanceId, "phoneNumber", customerInfo.getPhoneNumber());
    	runtimeService.setVariable(processInstanceId, "eMail", customerInfo.geteMail());
    	
    	runtimeService.setVariable(processInstanceId, "customerInformationComplete", true);
    	
    	//wait for first interface response
    	
    	//redirect to next view
		return "redirect:/"  + processInstanceId + "/pick-an-insurance";
    }

}