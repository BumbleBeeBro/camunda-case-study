package com.group5.BVIS.Cars;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.group5.BVIS.Contracts.Contract;

/**
 *  Class to model a Car object. Car entity.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car implements java.io.Serializable {
	
	private static final long serialVersionUID = 42L;
	
	//Attributes
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String image;
	
	@NotNull
	private String name;
	
	@NotNull
	private double pricePerDay;
	
	@OneToMany(targetEntity=Contract.class)
	private List<Contract> contractList;
	
	@OneToMany(targetEntity=CarRental.class)
	private List<CarRental> carRentalList;
	
	//for JPA
	public Car() {
		
	}
	
	/**
	 * Constructor for a Car object.
	 * @param image, String. File name of a car image that is displayed during the car selection.
	 * @param name, String. Name of the car.
	 * @param pricePerDay, double. Price of the rental car for one day.
	 */
	public Car(String image, String name, double pricePerDay) {
		super();
		this.image = image;
		this.name = name;
		this.pricePerDay = pricePerDay;
	}
	
	//Getter/Setter
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	
	/**
	 * Method to display a Car object as a String, for logging
	 * @return String of the Car objects' attributes.
	 */
	@Override
	public String toString() {
		return "Car: " + " " + id + " " + name + " " + pricePerDay + " localhost:8080/" + image;
		
	}
}
