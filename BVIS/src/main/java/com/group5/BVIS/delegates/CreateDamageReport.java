package com.group5.BVIS.delegates;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.Contracts.Contract;
import com.group5.BVIS.DamageReport.DamageReport;

/**
 * Implements service task "Create Damage Report" in "BVISDamageHandling" BPMN.
 * Creates and stores a damage report.
 */
public class CreateDamageReport implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		
		//get the contract ID.
		Long contractId = (Long) execution.getVariable("contractId");
		
		try {
			//get Contract by calling the contract WS.
			Contract contract = restTemplate.getForObject("http://localhost:8080/contracts/" + contractId, Contract.class);
			
			//get damage classification.
			Long damageClassification = (Long) execution.getVariable("damageClassification");
			
			//create new damage report.
			DamageReport report = new DamageReport(contract.getCustomerId(), contract, damageClassification.intValue());
			
			//store new Damage report by calling the damage report WS.
			Long reportId = restTemplate.postForObject("http://localhost:8080/damage-reports/create", report, Long.class);
			
			//may override previous reports, thus their IDs are separately saved.
			execution.setVariable("damageReport", restTemplate.getForObject("http://localhost:8080/damage-reports/" + reportId, DamageReport.class));
			
		} catch (Exception e) {
			LOGGER.info("Could not find the contract, Please create a new, valid one damage handling process instance");
			
			execution.setVariable("contractNotFound", true);
			
		}
	}

}
