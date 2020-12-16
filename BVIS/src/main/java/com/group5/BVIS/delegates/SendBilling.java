package com.group5.BVIS.delegates;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.Billings.Billing;

/**
 * Implements send task "Send billing to customer" in "BVISContracting" BPMN.
 * Triggers the sending of the bill to the customer via eMail.
 */
@ComponentScan("com.group5.BVIS.EmailSender")
public class SendBilling implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {	
		
		RestTemplate restTemplate = new RestTemplate();
		
		//get ID.
		Long billingId = (Long) execution.getVariable("billingId");
		
		//get the billing.
		String billing = restTemplate.getForObject("http://localhost:8080/billings/" + billingId, Billing.class).toString();
		
		//creates url for sending.
		String url = "http://localhost:8080/email/" + "089719lkdfgbhrtbhzerzbgthjfno82knv?to=" + (String) execution.getVariable("eMail") + "&subject=" + "Your BVIS contract" + execution.getVariable("contractId") + "_" + execution.getVariable("customerType") + "&text=" + billing;
		
		System.out.println(url);
		
		//send to mail web service
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		
		//if mailing failed log.
		if(response.getStatusCode() !=  HttpStatus.OK) {
			LOGGER.info("could not send contract to customer");
		} else {
			LOGGER.info("successfully sent bill to customer!");
		}
	}

}
