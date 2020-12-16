package com.group5.BVIS.delegates;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;


/**
 * Implements send task "Send contract to customer" in "BVISContracting" BPMN.
 * Triggers the sending of the contract to the customer via eMail.
 */
@ComponentScan("com.group5.BVIS.EmailSender")
public class SendContractToCustomer implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {	
				
		String contract =  (String) execution.getVariable("contractText");
		
		RestTemplate restTemplate = new RestTemplate();
		
		//send contract to customer, do it with a rest service because autowired beans cant be 
		//accessed due to new instantiation of the delegate by Camunda.
		//create email components.		
		String url = "http://localhost:8080/email/" + "089719lkdfgbhrtbhzerzbgthjfno82knv?to=" + (String) execution.getVariable("eMail") + "&subject=" + "Your BVIS contract" + execution.getVariable("contractId") + "_" + execution.getVariable("customerType") + "&text=" + contract;
		
		//send to mail web service.
		ResponseEntity<String> response = restTemplate.getForEntity(url, null);
		
		//if mailing failed log.
		if(response.getStatusCode() !=  HttpStatus.OK) {
			LOGGER.info("could not send contract to customer");
		} else {
			LOGGER.info("successfully sent contract to customer!");
		}
	}
}
