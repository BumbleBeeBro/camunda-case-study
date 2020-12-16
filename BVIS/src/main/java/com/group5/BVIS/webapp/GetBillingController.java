package com.group5.BVIS.webapp;


import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.webapp.formModels.GetBilling;

import java.util.ArrayList;

import java.util.logging.Logger;

import javax.validation.Valid;

/**
 * Deprecated, now the file is directly sent via email, site can be used to redownload bills.
 */
@Deprecated
@Controller
public class GetBillingController {
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Autowired
	private RuntimeService runtimeService;

    @GetMapping("/get-billing")
    public String customerInfo(Model model){
    	
    	LOGGER.info("Someone requested a billing");
    	
        return "get-billing";
    }
    
    /**
     * Get the requested billing from the user form.
     * @param model
     * @param getBilling
     * @param bindingResult
     * @return the billing view, with the bill.
     */
    @PostMapping("/get-billing")
    public String IndexPrivate(Model model,  @Valid GetBilling getBilling, BindingResult bindingResult) {
    	
    	//errorhandling
    	if (bindingResult.hasErrors()) {
    		ArrayList<String> errors = new ArrayList<String>();
    		for(ObjectError error : bindingResult.getAllErrors()) {
    			errors.add(error.getDefaultMessage());
    		}
    		model.addAttribute("errors", errors);
            return "get-billing";
        }
    	
    	try {
    		Long billingId = (Long) runtimeService.getVariable(getBilling.getCustomerId(), "billingId");
    		return "redirect:/billings/" + billingId + "/file";
    		
		} catch (Exception e) {
			LOGGER.info("could not find any insurances for your Id" + e.toString());
			
			model.addAttribute("error", "offerings connection crashed, sorry for the inconvenience");
			return "get-billing";
		}
    }   

}