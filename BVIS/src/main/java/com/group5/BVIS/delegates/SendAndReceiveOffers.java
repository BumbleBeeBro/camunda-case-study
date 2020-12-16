package com.group5.BVIS.delegates;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.RestModels.CustomerRequirements;
import com.group5.BVIS.RestModels.InsuranceOffering;

/**
 * Implements send tasks "Send customer Requirements to Captiol" in "BVISContracting" BPMN.
 * This delegate is referenced in the private customer path and the business customer path.
 * Sends the requirements given by the customer to Capitol and receives possible insurance offers as a response.
 * Implements the first and second interface.
 */
public class SendAndReceiveOffers implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		//get necessary variables from the process that are needed to send the customer requirements (custo)
		String customer_id = execution.getProcessInstanceId();
		
		String customer_type = (String) execution.getVariable("customerType");
		
		Long rental_duration = ((Date) execution.getVariable("returnDate")).getTime() -  ((Date) execution.getVariable("pickupDate")).getTime();
		
		Long vehicle_model = (Long) execution.getVariable("carId");
		
		Long number_of_vehicles =  new Long((int) execution.getVariable("number_of_vehicles"));
			
		Date birth_date = (Date) execution.getVariable("birthDate");
		
		String name = (String) execution.getVariable("name");
		
		String address = (String) execution.getVariable("address");
		
		String phone_number = (String) execution.getVariable("phoneNumber");
		
		String e_mail = (String) execution.getVariable("eMail");
		
		
		//create the customer requirements as an object.
		CustomerRequirements custo = new CustomerRequirements(customer_id, customer_type, rental_duration, vehicle_model, number_of_vehicles, birth_date, name, address, phone_number, e_mail);
		
		RestTemplate restTemplate = new RestTemplate();
		

		String response = null;
		
		//Send customer requirements to Capitol
		String e = restTemplate.postForEntity("http://192.168.43.17:8080/contract-process/BeginContractServlet", custo, String.class, response).getBody();

		//map results separately, as an ArrayList was not possible to be mapped to a Java Object by the restTemplate.
		ObjectMapper mapper = new ObjectMapper();
		
		List<InsuranceOffering> offerings = mapper.readValue(e, new TypeReference<List<InsuranceOffering>>(){});		
		
		//set offerings as process variable
		execution.setVariable("offerings", Variables.objectValue(offerings).serializationDataFormat("application/json").create());
	
		LOGGER.info(offerings.toString());
		
	}

}
