package com.group5.BVIS.delegates;

import java.util.HashMap;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;

/**
 * Implements service task "Trigger damage handling" in "BVISContracting" BPMN.
 * Sends a signal to start a damage handling process after car is returned.
 */
public class TriggerDamageHandling implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {	
		
		RestTemplate restTemplate = new RestTemplate();
		
		//Signals the damage handling process to start. Camunda requires an empty JSON.
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/rest/process-definition/key/DamageHandling/start", new HashMap<>(), null);
		
		if(response.getStatusCode() !=  HttpStatus.OK) {
			LOGGER.info("could not start damage handling");
		} else {
			LOGGER.info("successfully started damage handling process!");
		}
	}

}
