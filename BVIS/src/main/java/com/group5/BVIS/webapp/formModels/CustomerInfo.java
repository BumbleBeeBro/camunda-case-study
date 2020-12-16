package com.group5.BVIS.webapp.formModels;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Represents the html input form as a Java class.
 * Customer Info View.
 */
public class CustomerInfo {

	//map form fields
	@NotNull(message = "Please fill out all fields")
	private String firstName;
	
	@NotNull(message = "Please fill out all fields")
	private String lastName;
	
	//at least 18?
	@DateTimeFormat(iso = ISO.DATE)
	@NotNull(message = "Please fill out all fields")
	private Date birthDate;
	
	@NotNull(message = "Please fill out all fields")
	private String streetName;
	
	@NotNull(message = "Please fill out all fields")
	private String houseNumber;
	
	@Digits(fraction = 1, integer = 5, message = "this zip code is not valid")
	@NotNull(message = "Please fill out all fields")
	private String zipCode;
	
	@NotNull(message = "Please fill out all fields")
	private String city;
	
	
	@Digits(fraction = 2, integer = 15, message = "This phone number is not valid")
	@NotNull(message = "Please fill out all fields")
	private String phoneNumber;
	
	@Email(message = "this email is not valid")
	@NotNull(message = "Please fill out all fields")
	private String eMail;
	
	/**
	 * Check if customer is at least 18 years old.
	 * @return boolean that describes whether conditionis satisfied.
	 */
	@NotNull(message = "Please fill out all fields")
	@AssertTrue(message = "You need to be at least 18 to rent a car")
	public boolean isConditionTrue() {
		
		try {
			Date date = new Date();
		date.setYear((new Date().getYear()) - 18);
		
		System.out.println(date.toString());
		
		return (birthDate.getTime() - date.getTime()) < 0 ;
		} catch (Exception e) {
			return false;
		}
	}
	
	//Getter/Setter
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public  String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * Returns the values of the customer information as a String.
	 */
	@Override
	public String toString() {
		return "CustomerInfo HTML Form: " + firstName + " " + lastName + " " + birthDate + " " + streetName + " " + houseNumber + " " + zipCode + " " + city + " " + phoneNumber + " " + eMail;
	}
	
}
