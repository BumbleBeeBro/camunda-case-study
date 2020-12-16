package com.group5.BVIS.webapp.formModels;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.FutureOrPresent;

/**
 * Represents the html input form as a Java class.
 * PickupAndReturn View.
 */
public class PickupAndReturn {
	
	//Map form fields
	@Size(min=2, max=30, message = "Pickup location must be between 2 and 30 characters")
	@NotNull(message = "Please fill out all fields")
	private String pickupLocation;
	
	
	@Size(min=2, max=30, message = "Return location must be between 2 and 30 characters")
	@NotNull(message = "Please fill out all fields")
	private String returnLocation;
	
	@NotNull(message = "Please fill out all fields")
	@FutureOrPresent(message = "The return date must be a date in the future")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date pickupDate;
	
	@NotNull(message = "Please fill out all fields")
	@FutureOrPresent(message = "The return date must be a date in the future")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date returnDate;
	
	/**
	 * Checks if the pickup date is before the return date.
	 * @return boolean, signals whether the condition is satisfied.
	 */
	@NotNull(message = "Please fill out all fields")
	@AssertTrue(message = "The pickup date musst be before the return date")
	public boolean isConditionTrue() {
		try {
			return pickupDate.getTime() < returnDate.getTime();
		} catch (Exception e) {
			return true;
		}
	}
	
	//Getter/Setter
	public String getPickupLocation() {
		return pickupLocation;
	}
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public String getReturnLocation() {
		return returnLocation;
	}
	public void setReturnLocation(String returnLocation) {
		this.returnLocation = returnLocation;
	}
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	/**
	 * Returns the values of the form as a String.
	 */
	@Override
	public String toString() {
		return "CustomerInfo HTML Form: " + pickupLocation + " " + returnLocation + " " + pickupDate + " " + returnDate;
	}
	
}
