package com.group5.BVIS.delegates;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.RestModels.OverallDamageAssessment;
import com.group5.BVIS.RestModels.SignalContractEnd;


/**
 * Implements receive task "Receive insurance reimbursement" in "BVISContracting" BPMN.
 * Send capitol a signal so that they can calculate the reimbursement and send it.
 * Receives reimbursement.
 * Implements sixth interface, now with insurance reimbursement.
 */
public class SignalContractEndAndReceiveOverallDamageAssessment implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		
		//get customer ID.
		String customer_id = execution.getProcessInstanceId();
		
		SignalContractEnd signalContractEnd = new SignalContractEnd(customer_id);

		//Send signal and get damage assessment.
		OverallDamageAssessment assessment = restTemplate.postForObject("http://192.168.43.17:8080/contract-process/ContractEndServlet" , signalContractEnd, OverallDamageAssessment.class);	
		
		LOGGER.info(assessment.toString());
		
		//set the damageCharges.
		execution.setVariable("damageCharges", assessment.getFinal_payment());
		
		//calculate the reimbursement money. This replaces the "ReceiveInsruanceReimbrusement" task and RESTModel that where previously used to receive the reimbursement. 
		execution.setVariable("insuranceMoney", (assessment.getDamage_amount() - (assessment.getFinal_payment() + assessment.getClaim_fee())));
		
	}

}
