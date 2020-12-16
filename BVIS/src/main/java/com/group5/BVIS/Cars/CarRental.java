package com.group5.BVIS.Cars;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.group5.BVIS.Contracts.Contract;
/**
 * Class to model a CarRental object. CarRental entity.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CarRental implements java.io.Serializable {

	private static final long serialVersionUID = 42L;
	
	//Attributes
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne(targetEntity=Car.class)
	private Car car;
	
	@NotNull
	@ManyToOne(targetEntity=Contract.class)
	private Contract contract;
	
	@NotNull
	private Date rentalStart;
	
	private Date rentalEnd;
	
	/**
	 * Constructor for a CarRental object.
	 * @param car, Car. Car that is rented.
	 * @param contract, Contract. Related contract.
	 * @param rentalStart, Date. Start of the rental agreement.
	 */
	public CarRental(@NotNull Car car, @NotNull Contract contract, @NotNull Date rentalStart) {
		super();
		this.car = car;
		this.contract = contract;
		this.rentalStart = rentalStart;
	}
		
	//for JPA
	public CarRental() {
		
	}

	//Getter/Setter
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Date getRentalStart() {
		return rentalStart;
	}

	public void setRentalStart(Date rentalStart) {
		this.rentalStart = rentalStart;
	}

	public Date getRentalEnd() {
		return rentalEnd;
	}

	public void setRentalEnd(Date rentalEnd) {
		this.rentalEnd = rentalEnd;
	}

	/**
	 * Method to display a CarRental object and its attributes as a String for logging.
	 * @return String of the CarRental object.
	 */
	@Override
	public String toString() {
		return "CarRental [id=" + id + ", car=" + car + ", contract=" + contract + ", rentalStart=" + rentalStart
				+ ", rentalEnd=" + rentalEnd + "]";
	}
	
	
}
