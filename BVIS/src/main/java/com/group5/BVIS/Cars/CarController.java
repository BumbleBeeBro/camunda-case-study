package com.group5.BVIS.Cars;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller Class for Car. Manages cars.
 */
@RestController
public class CarController {
	
	//Necessary repositories autowired.
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private CarRentalRepository carRentalRepository;
	
	
	/**
	 * Return all cars in the database. Also simulates that not always all cars are available, and thus randomly removes one car from the list.
	 * @return list of Cars.
	 */
	@CrossOrigin(origins = "*")
	@GetMapping("/cars")
    public List<Car> carFindAll() {
        return carRepository.findAll();
	}
	
	/**
	 * Return the Car with a certain ID.
	 * @param id, Long. ID of requested Car.
	 * @return return requested Car object.
	 */
	@GetMapping("/cars/{id}")
    public Car carFindOne(@PathVariable("id") Long id) {
        return carRepository.getOne(id);
	}
	
	/**
	 * Returns all CarRental objects in the database.
	 * @return return list of CarRentals.
	 */
	@GetMapping("/car-rentals")
	public List<CarRental> carRentalFindAll() {
		return carRentalRepository.findAll();
	}
	
	/**
	 * Return the CarRental object with a certain ID.
	 * @param id, Long. ID of requested CarRental.
	 * @return return requested CarRental object.
	 */
	@GetMapping("/car-rentals/{id}")
	public CarRental carRentalFindOne(@PathVariable("id") Long id) {
		return carRentalRepository.getOne(id);
	}
	
	/**
	 * Create a new database entry
	 * @param carRental, CarRental. The CarRental object to be saved
	 * @return returns the database ID of the object.
	 */
	@PostMapping("/car-rentals/create")
	public Long carRentalCreate(@RequestBody CarRental carRental) {
		
		return carRentalRepository.save(carRental).getId();
	}
	
	/**
	 * Set the RentalEnd of a CarRental object.
	 * @param id, long. ID of the selected CarRental object.
	 */
	@PostMapping("/car-rentals/add-rental-end/{id}")
	public void carRentalCreate(@PathVariable("id") Long id, @RequestBody Date rentalEnd) {
		
		CarRental carRental = carRentalRepository.getOne(id);
		
		carRental.setRentalEnd(rentalEnd);
		
		carRentalRepository.save(carRental);
	}
}