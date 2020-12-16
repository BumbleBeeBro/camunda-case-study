package com.group5.BVIS.webapp;


import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.group5.BVIS.LoggerDelegate;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Controller for Index View, starts the process in Camunda for a private Customer..
 */
@Controller
public class IndexController {
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Autowired
	private RuntimeService runtimeService;

	/**
	 * Controller for initial get request. First contact point for customer.
	 * @param model, Model. Page model.
	 * @return index view.
	 */
    @GetMapping("/")
    public String customerInfo(Model model){
    	
    	LOGGER.info("Someone entered the site");
    	
        return "index";
    }
    
    /**
     * Controller for starting of new private process instance.
     * @param model, Model. Page model.
     * @return next view.
     */
    @GetMapping("/start-private-rent")
    public String IndexPrivate(Model model) {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	//mark customer as private
    	map.put("customerType", "private");
    	
    	//start process
    	String processInstanceId = runtimeService.startProcessInstanceByKey("BVIS", map).getId();
    	
    	//redirect
		return "redirect:/" + processInstanceId + "/pickup-and-return";
    	
    }
    
    /**
     * Controller for business contact.
     * @param model, Model. Page model.
     * @return next view.
     */
    @GetMapping("/start-business-rent")
    public String IndexBusiness(Model model) {
    	 
    	// return next view.
		return "business-contact";
    	
    }
    
    

}