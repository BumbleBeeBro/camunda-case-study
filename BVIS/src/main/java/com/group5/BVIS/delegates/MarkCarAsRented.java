package com.group5.BVIS.delegates;


import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;


import com.group5.BVIS.LoggerDelegate;
import com.group5.BVIS.Cars.Car;
import com.group5.BVIS.Cars.CarRental;
import com.group5.BVIS.Contracts.Contract;

/**
 * Implements service task "Mark car as rented" in "BVISContracting" BPMN.
 * Creates a CarRental object that marks a ongoing rental.
 */
public class MarkCarAsRented implements JavaDelegate{
	
	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {	
		
		RestTemplate restTemplate = new RestTemplate();
		
		//get car ID.
		Long carId = (Long) execution.getVariable("carId");
		
		//get associated car model.
		Car car = restTemplate.getForObject("http://localhost:8080/cars/" + carId, Car.class);
		
		//get contract ID.
		Long contractId = (Long) execution.getVariable("contractId");
		
		//get associated contract.
		Contract contract = restTemplate.getForObject("http://localhost:8080/contracts/" + contractId, Contract.class);
		
		Date rentalStart = new Date();
		
		//create the car rental object.
		CarRental carRental = new CarRental(car, contract, rentalStart);
		
		//store car rental object in database.
		Long carRentalId = restTemplate.postForObject("http://localhost:8080/car-rentals/create/", carRental, Long.class);
		
		//set ID. as process variable.
		execution.setVariable("carRentalId", carRentalId);
		
		LOGGER.info("Car rental created: " + carRentalId);
		
	}

}
