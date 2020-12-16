package com.group5.BVIS.delegates;


import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.web.client.RestTemplate;


import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.Billings.Billing;
import com.group5.BVIS.Contracts.Contract;

/**
 * Implements service task "Create Billing" in "BVISContracting" BPMN. 
 * Creates a billing object that links the damage charges with a contract.
 */
public class CreateBilling implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {	
				
		RestTemplate restTemplate = new RestTemplate();
		
		//get associated contract ID.
		Long contractId = (Long) execution.getVariable("contractId");
		
		//get associated contract, by calling the contracting WSS.
		Contract contract = restTemplate.getForObject("http://localhost:8080/contracts/" + contractId, Contract.class);
		
		//get associated damage charges.
		double damageCharges = (double) execution.getVariable("damageCharges");
		
		//create Billing.
		Billing billing = new Billing(contract, damageCharges);
		
		//store Billing by calling the billing WS.
		Long billingId = restTemplate.postForObject("http://localhost:8080/billings/create", billing, Long.class);
		
		//set billingId as process variable
		execution.setVariable("billingId", billingId);
		
		LOGGER.info("Billing created: " + billingId);
	}

}
