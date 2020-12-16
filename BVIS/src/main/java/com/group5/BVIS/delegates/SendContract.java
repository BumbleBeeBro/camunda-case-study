package com.group5.BVIS.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.RestModels.ContractSending;

/**
 * Implements send task "Send contract to Capitol" in "BVISContracting" BPMN.
 * Sends the contract to Capitol via RESTApi.
 * Implements the third interface.
 */
public class SendContract implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {	
		
		//get the customer ID.
		String customer_id = execution.getProcessInstanceId();
		
		//get the selected insurance.
		int offer_id = (int) execution.getVariable("insuranceId");
		
		// get the text of the contract.
		String contract =  (String) execution.getVariable("contractText");
		
		//crate a ContractSending object to bundle the data.
		ContractSending contractSending = new ContractSending(customer_id, offer_id, contract);
		
		RestTemplate restTemplate = new RestTemplate();		
		
		//Send contract to Capitol. No response required.
		restTemplate.postForLocation("http://192.168.43.17:8080/contract-process/ConfirmationServlet", contractSending);
		
	}

}
