package com.group5.BVIS;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class ScheduledTasks {

	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
		

	/**
	 * Method to delete unused process instances. This method is used to clean up all process instances
	 * that have not resulted in a contract at a certain point. This is used to get rid of process instances that
	 * were terminated early. 
	 */
	//Setting the time to start the cron job ( sec, min, hour, day, month, day of week).
	@Scheduled(cron="0 55 23 * * SUN")
    public void deleteUnusedWebsiteCreatedProcessInstances () {
    	LOGGER.info("starting to delete process instances");
    	RestTemplate restTemplate = new RestTemplate();
    	
		//get instances, via rest call
		ArrayList array = restTemplate.getForObject("http://localhost:8080/rest/process-instance/", ArrayList.class);
    	
    	//iterate through the instances.
		for (int i = 0; i < array.size(); i++) {
    		
    		//fetch ID
			HashMap<String,Object> map = (HashMap<String, Object>) array.get(i);
    		String currentId = map.get("id").toString();
    		 
    		try {
    			//get process instance and variables
    			HashMap<String, Object> variables = restTemplate.getForObject("http://localhost:8080/rest/process-instance/" + currentId + "/variables", HashMap.class);
    			
    			HashMap<String,Object> customerType = (HashMap<String, Object>) variables.get("customerType");

    			//check for completed contract and type of customer
    			if(variables.get("contractAccepted") == null && customerType.get("value") == "private") {
    				LOGGER.info("Process instance with id: " + currentId + " will be deleted");
    				//delete process instance
    				restTemplate.delete("http://localhost:8080/rest/process-instance/" + currentId);
    			}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
    }
}