package com.group5.BVIS.delegates;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.Contracts.Contract;
import com.group5.BVIS.RestModels.InsuranceFees;

/**
 * Implements send task "Send insurance fees" in "BVISContracting" BPMN.
 * Sends the insurance fees to Capitol.
 * Implements seventh interface. 
 */
@ComponentScan("com.group5.BVIS.EmailSender")
public class SendInsuranceFees implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

	@Override
	public void execute(DelegateExecution execution) throws Exception {	
		
		RestTemplate restTemplate = new RestTemplate();
		
		//get necessary data from the process instance.
		String customer_id = execution.getProcessInstanceId();
		
		Long contractId = (Long) execution.getVariable("contractId");
		
		Contract contract = restTemplate.getForObject("http://localhost:8080/contracts/" + contractId, Contract.class);
		
		double insurance_money = contract.getTotal_insurance_price();
		
		InsuranceFees fees = new InsuranceFees(customer_id, insurance_money);
		
		//Send fees to Capitol.
		ResponseEntity<Object> response = restTemplate.postForEntity("http://192.168.43.17:8080/contract-process/PaymentServlet", fees, null);

		if(response.getStatusCode() !=  HttpStatus.OK) {
			LOGGER.info("could not send damage report");
		} else {
			LOGGER.info("successfully sent damage report to Capitol!");
		}
	}

}
