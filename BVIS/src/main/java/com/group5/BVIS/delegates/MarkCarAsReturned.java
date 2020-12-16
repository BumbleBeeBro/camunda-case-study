package com.group5.BVIS.delegates;


import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;
/**
 * Implements service task "mark car as returned" in "BVISContracting" BPMN.
 * Marks that a customer has returned a car.
 */
public class MarkCarAsReturned implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {	
			
		RestTemplate restTemplate = new RestTemplate();
		
		//get ID of car rental.
		Long carRentalId = (Long) execution.getVariable("carRentalId");
		
		//add the end date of the rental.
		restTemplate.postForLocation("http://localhost:8080/car-rentals/add-rental-end/" + carRentalId, new Date());
		
		LOGGER.info("Added end date to Car rental: " + carRentalId);
		
	}

}
