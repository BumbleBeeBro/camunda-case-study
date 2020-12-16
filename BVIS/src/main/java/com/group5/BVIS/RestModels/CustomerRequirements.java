package com.group5.BVIS.RestModels;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Implements REST model to send the customer requirements to Capitol in the required format.
 * Bundles necessary variables in an object.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequirements {

	public CustomerRequirements() {
		super();
	}

	//Attributes
	
	private String customer_id;
	private String customer_type;
	
	private Long rental_duration;
	private Long vehicle_model;
	
	private Long number_of_vehicles;

	private String birth_date;
	private String name;
	private String address;
	
	private String phone_number;
	
	private String e_mail;
	
	/**
	 * Constructor for CustomerRequirements.
	 * @param customer_id, String. ID of associated customer.
	 * @param customer_type, String. The type of associated customer. Either "private" or "business".
	 * @param rental_duration, Long. The duration of the rental agreement.
	 * @param vehicle_model, Long. ID of the selected vehicle model.
	 * @param number_of_vehicles, Long. The number of rented vehicles of the same type.
	 * @param birth_date, Date. Birth date of the associated customer.
	 * @param name, String. Name of associated customer, first and last name.
	 * @param address, String. Address of the associated customer.
	 * @param phone_number, String. Phone number of the customer
	 * @param e_mail, String. E-Mail address of thhe customer.
	 */
	public CustomerRequirements(String customer_id, String customer_type, Long rental_duration, Long vehicle_model, Long number_of_vehicles,
			Date birth_date, String name, String address, String phone_number, String e_mail) {
		super();
		this.customer_id = customer_id;
		this.customer_type = customer_type;
		this.rental_duration = rental_duration;
		this.vehicle_model = vehicle_model;
		this.number_of_vehicles = number_of_vehicles;
		this.birth_date = birth_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));;
		this.name = name;
		this.address = address;
		this.phone_number = phone_number;
		this.e_mail = e_mail;
	}
	
	
	//Getter/Setter
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_type() {
		return customer_type;
	}
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	public Long getRental_duration() {
		return rental_duration;
	}
	public void setRental_duration(Long rental_duration) {
		this.rental_duration = rental_duration;
	}
	public Long getVehicle_model() {
		return vehicle_model;
	}
	public void setVehicle_model(Long vehicle_model) {
		this.vehicle_model = vehicle_model;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getaddress() {
		return address;
	}
	public void setaddress(String address) {
		this.address = address;
	}

	public Long getNumber_of_vehicles() {
		return number_of_vehicles;
	}

	public void setNumber_of_vehicles(Long number_of_vehicles) {
		this.number_of_vehicles = number_of_vehicles;
	}

	/**
	 * Returns the CustomerRequirements object with its attributes as a String.
	 */
	@Override
	public String toString() {
		return "CustomerRequirements [customer_id=" + customer_id + ", customer_type=" + customer_type
				+ ", rental_duration=" + rental_duration + ", vehicle_model=" + vehicle_model + ", number_of_vehicles="
				+ number_of_vehicles + ", birth_date=" + birth_date + ", name=" + name + ", address=" + address + "]";
	}


	public String getPhone_number() {
		return phone_number;
	}


	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}


	public String getE_mail() {
		return e_mail;
	}


	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}	
}
