package com.group5.BVIS.delegates;


import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.group5.BVIS.LoggerDelegate;

/**
 * Implements service task "Determine if private or business customer" in "BVISContracting" BPMN.
 * Determines whether the process was started via the website or the camunda tasklist and directs the process path.
 */
public class DetermineCustomer implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {	
		
		//check whether the variable "customerType" is already set.
		if (execution.getVariable("customerType") == null) {
			
			//set variable to "business". This is done when the process is started manually in the Camunda tasklist.
			execution.setVariable("customerType", "business");
			
			LOGGER.info("New business customer created");
			
		} else {
			
			//variable is already set to "private". This means that the process was started from the website.
			LOGGER.info("New private customer created");
		}
		
	}

}
