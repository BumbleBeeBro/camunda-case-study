package com.group5.BVIS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.group5.BVIS.Cars.Car;
import com.group5.BVIS.Cars.CarRepository;

/**
 * Class to populate the database that store the car models available. 
 * The database is artificially populated before the workflow starts, as we use a in memory database. 
 */
@Component
public class PopulatingDatabases implements ApplicationRunner {

	@Autowired
	private CarRepository carRepository;	
	
	/**
	 * Function to store five car models into the carRepository using the spring framework.
	 */
	public void run(ApplicationArguments args) {
				
		carRepository.save(new Car("audi-a3.png", "Audi A3", 34.99));
		carRepository.save(new Car("ford-mustang.png", "Ford Mustang", 44.99));
		carRepository.save(new Car("mercedes-benz-cla.png", "Mercedes-Benz CLA", 49.99));		
		carRepository.save(new Car("bmw-i8.png", "BMW i8", 69.99));
		carRepository.save(new Car("lamborghini-gallardo.png", "Lamborghini Gallardo", 99.99));
    }
}