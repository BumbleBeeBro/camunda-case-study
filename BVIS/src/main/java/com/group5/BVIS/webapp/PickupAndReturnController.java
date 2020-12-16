package com.group5.BVIS.webapp;


import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.webapp.formModels.PickupAndReturn;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.validation.Valid;

/**
 * Controller for PickupAndReturn View.
 */
@Controller
public class PickupAndReturnController {
	
	//autowire runtime service.
	@Autowired
	private RuntimeService runtimeService;
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

	/**
	 * Controller for pickup and return. Get request to site. 
	 * @param processInstanceId, String. ID of process instance.
	 * @param model, Model. Page model.
	 * @return PickupAndReturn View.
	 */
    @GetMapping("/{processInstanceId}/pickup-and-return")
    public String customerInfo(@PathVariable("processInstanceId") String processInstanceId, Model model){
    	
    	//see if there is already data that only needs editing
    	//add the variables to model.
    	if (runtimeService.getVariable(processInstanceId, "customerInformationComplete") != null) {
    		model.addAttribute("pickupLocation", runtimeService.getVariable(processInstanceId, "pickupLocation"));
    		model.addAttribute("returnLocation", runtimeService.getVariable(processInstanceId, "returnLocation"));
    		model.addAttribute("pickupDate", ((Date) runtimeService.getVariable(processInstanceId, "pickupDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    		model.addAttribute("returnDate", ((Date) runtimeService.getVariable(processInstanceId, "returnDate")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		}
    	
    	model.addAttribute("processInstanceId", processInstanceId);
        
    	//return PickupAndReturn View.
    	return "pickup-and-return";
    }
    
    /**
     * Set date format.
     * needed to convert a html date (transferred as a string) to a java.util.date date.
     * @param binder, WebDataBinder.
     */
    @InitBinder     
    public void initBinder(WebDataBinder binder){
         binder.registerCustomEditor(       Date.class,     
                             new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"), true, 16));   
    }
    
    /**
     * Controller for pickup and return. Handle user input form submitted via post.
     * @param processInstanceId, String. Current process instance ID. Serves as customer ID.
     * @param pickupAndReturn,PickupAndReturn. Form model.
     * @param bindingResult, BindingResult. Used for validation.
     * @param model, Model. Page model.
     * @return redirect to the next view.
     */
    @PostMapping("/{processInstanceId}/pickup-and-return")
    public String customerInfoSubmit(@PathVariable("processInstanceId") String processInstanceId, @Valid PickupAndReturn pickupAndReturn, BindingResult bindingResult, Model model) {
       	
    	//errorhandling
    	if (bindingResult.hasErrors()) {
    		ArrayList<String> errors = new ArrayList<String>();
    		for(ObjectError error : bindingResult.getAllErrors()) {
    			errors.add(error.getDefaultMessage());
    		}
    		model.addAttribute("errors", errors);
            return "pickup-and-return";
        }
    	
    	//Set the input data as process variables.
    	runtimeService.setVariable(processInstanceId, "pickupLocation", pickupAndReturn.getPickupLocation());
    	
    	runtimeService.setVariable(processInstanceId, "returnLocation", pickupAndReturn.getReturnLocation());
    	
    	runtimeService.setVariable(processInstanceId, "pickupDate", pickupAndReturn.getPickupDate());
    	
    	runtimeService.setVariable(processInstanceId, "returnDate", pickupAndReturn.getReturnDate());
    	
    	   	
    	LOGGER.info(pickupAndReturn.toString());
    	
    	//redirect to next view.
		return "redirect:/{processInstanceId}/pick-a-car";
    }
    
    
    
    

}