package com.group5.BVIS.delegates;

import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.Cars.Car;

public class Dummy implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	/**
	 * A Dummy for testing, prints out all variables.
	 * Deprecated, not used anymore
	 */
	@Deprecated
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		execution.getVariables().forEach((key, value) -> {
			LOGGER.info("PrintOutVariablesDelegate: " + "Key : " + key + " Value : " + value.toString());
		});
		
	}

}
