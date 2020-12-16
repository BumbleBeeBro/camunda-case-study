package com.group5.BVIS.delegates;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.DamageReport.DamageReport;
import com.group5.BVIS.RestModels.DamageReportSending;


public class SendDamageReport implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Value("${restEndpoint}")
	private String restEndpoint;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		//get latest damage report
		DamageReport report = (DamageReport) execution.getVariable("damageReport");
		
		DamageReportSending reportSending = new DamageReportSending(report.getCustomerId(), report.getDamageClassification());
		
		RestTemplate restTemplate = new RestTemplate();
		
		//Send DamageReport and receive status:
		ResponseEntity<Object> response = restTemplate.postForEntity("http://192.168.43.17:8080/contract-process/BeginClaimServlet", reportSending, null);

		//log depending on success of the rest call.
		if(response.getStatusCode() !=  HttpStatus.OK) {
			LOGGER.info("could not send damage report");
		} else {
			LOGGER.info("successfully sent damage report to Capitol!");
		}
		
	}

}
