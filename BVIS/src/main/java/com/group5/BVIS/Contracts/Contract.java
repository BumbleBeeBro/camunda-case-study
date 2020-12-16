package com.group5.BVIS.Contracts;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.group5.BVIS.Cars.Car;
import com.group5.BVIS.DamageReport.DamageReport;




/**
 * Class to model a Contract object. Contract entity.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contract implements java.io.Serializable {
	
	private static final long serialVersionUID = 42L;
	
	// for JPA
	public Contract() {	
		
	}

	//Attributes
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String customerId;
	
	@NotNull
	private String customer_type;
	
	private String name;
	
	@NotNull
	private String address;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER, optional = true)
	private Car car;
	
	@NotNull
	private Long number_of_vehicles;
	
	@NotNull
	private String pickup_location;

	@NotNull
	private String return_location;
	
	@NotNull
	private String pickup_date;
	
	@NotNull
	private String return_date;
	
	@NotNull
	private String insurance_name;
	
	@NotNull
	private double price_per_day;
	
	@NotNull
	private double insurance_price_per_day;
	
	@NotNull
	private double total_price;
	
	@NotNull
	private double total_insurance_price;
	
	@OneToMany(targetEntity=DamageReport.class)
	private List<DamageReport> damageReportList;
	
	/**
	 * Constructor for a Contract object.
	 * @param customer_id, String. ID of the associated customer.
	 * @param customer_type, String. Marks the type of the customer. Either "private" or "business".
	 * @param name, String. Name of the customer.
	 * @param address, String. Address of the customer.
	 * @param car, Car. Car model rented by the customer.
	 * @param number_of_vehicles, Long. Number of vehicles of a car type rented by the customer.
	 * @param pickup_location, String. Location of the car pickup.
	 * @param return_location, String. Location of the car return.
	 * @param pickup_date, Date. Date of the vehicle pickup.
	 * @param return_date, Date. Date of the vehicle return.
	 * @param insurance_name, String. Name of the insurance selected by the customer.
	 * @param price_per_day, double. Price of the selected vehicle per day.
	 * @param insurance_price_per_day, double. Price of the selected insurance per day.
	 */
	public Contract(String customer_id, String customer_type, String name, String address,
			Car car, Long number_of_vehicles, String pickup_location, String return_location, Date pickup_date, Date return_date,
			String insurance_name, double price_per_day, double insurance_price_per_day) {
		super();
		this.customerId = customer_id;
		this.customer_type = customer_type;
		this.name = name;
		this.address = address;
		this.car = car;
		this.number_of_vehicles = number_of_vehicles;
		this.pickup_location = pickup_location;
		this.return_location = return_location;
		this.pickup_date = pickup_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
		this.return_date = return_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
		this.insurance_name = insurance_name;
		this.price_per_day = Math.round((number_of_vehicles * price_per_day) * 100.0) / 100.0;
		this.insurance_price_per_day = Math.round((number_of_vehicles * insurance_price_per_day) * 100.0) / 100.0;
		
		total_insurance_price = Math.round((((return_date.getTime() - pickup_date.getTime()) * 0.0000000115741) * insurance_price_per_day) * 100.0) /100.0;
		
		total_price = Math.round((((return_date.getTime() - pickup_date.getTime()) * 0.0000000115741) * (price_per_day + insurance_price_per_day)) * 100.0) /100.0;
	}
	
	/**
	 * Display the contract and its attributes in a visual structure. This is a version for Java output and mailing.
	 * @return String containing the contract.
	 */
	@Override
	public String toString() { 
		return 	"Contract: " + id + "_"+ customer_type + "\r\n" +
				"----------------------------------------" + "\r\n" +
				"Name:             " + name + "\r\n" +
				"Address:          " + address + "\r\n" +
				"----------------------------------------" + "\r\n" +
				"Pickup:  " + pickup_date + " at: "+ pickup_location + "\r\n" +
				"Return:  " + return_date + " at: "+ return_location + "\r\n" +
				"----------------------------------------" + "\r\n" +
				"Car:              "+ car.getName() + " (" + number_of_vehicles + ")\r\n" +
				"Insurance:        " + insurance_name + "\r\n" +
				" Insurance       €" + price_per_day + "\r\n" +
				" Insurance price €" + insurance_price_per_day + "\r\n" +
				"----------------------------------------" + "\r\n" +
				"Overall          €" + total_price + "\r\n" +
				"----------------------------------------" + "\r\n" +
				"BVIS Ltd.";
		
		
	}


	//Getter/Setter
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customer_id) {
		this.customerId = customer_id;
	}


	public String getCustomer_type() {
		return customer_type;
	}


	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Car getCar() {
		return car;
	}


	public void setCar(Car car) {
		this.car = car;
	}


	public String getPickup_location() {
		return pickup_location;
	}


	public void setPickup_location(String pickup_location) {
		this.pickup_location = pickup_location;
	}


	public String getReturn_location() {
		return return_location;
	}


	public void setReturn_location(String return_location) {
		this.return_location = return_location;
	}


	public String getPickup_date() {
		return pickup_date;
	}


	public void setPickup_date(String pickup_date) {
		this.pickup_date = pickup_date;
	}


	public String getReturn_date() {
		return return_date;
	}


	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}


	public String getInsurance_name() {
		return insurance_name;
	}


	public void setInsurance_name(String insurance_name) {
		this.insurance_name = insurance_name;
	}


	public double getPrice_per_day() {
		return price_per_day;
	}


	public void setPrice_per_day(Float price_per_day) {
		this.price_per_day = price_per_day;
	}


	public double getInsurance_price_per_day() {
		return insurance_price_per_day;
	}


	public void setInsurance_price_per_day(Float insurance_price_per_day) {
		this.insurance_price_per_day = insurance_price_per_day;
	}


	public double getTotal_price() {
		return total_price;
	}


	public void setTotal_price(Float total_price) {
		this.total_price = total_price;
	}

	/**
	 * Outputs the attributes of a contract as a String for logging.
	 * @return String containing the attributes.
	 */
	public String toVariableString() {
		return "Contract [id=" + id + ", customerId=" + customerId
				+ ", customer_type=" + customer_type + ", name=" + name
				+ ", address=" + address + ", car=" + car.toString() + ", number_of_vehicles=" + number_of_vehicles
				+", pickup_location=" + pickup_location
				+ ", return_location=" + return_location + ", pickup_date=" + pickup_date + ", return_date="
				+ return_date + ", insurance_name=" + insurance_name + ", price_per_day=" + price_per_day
				+ ", insurance_price_per_day=" + insurance_price_per_day +", total_price=" + total_price + ", total_insurance_price=" + total_insurance_price + "]";
	}

	public Long getNumber_of_vehicles() {
		return number_of_vehicles;
	}

	public void setNumber_of_vehicles(Long number_of_vehicles) {
		this.number_of_vehicles = number_of_vehicles;
	}

	public void setPrice_per_day(double price_per_day) {
		this.price_per_day = price_per_day;
	}

	public void setInsurance_price_per_day(double insurance_price_per_day) {
		this.insurance_price_per_day = insurance_price_per_day;
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DamageReport> getDamageReportList() {
		return damageReportList;
	}

	public void setDamageReportList(List<DamageReport> damageReportList) {
		this.damageReportList = damageReportList;
	}

	public double getTotal_insurance_price() {
		return total_insurance_price;
	}

	public void setTotal_insurance_price(double total_insurance_price) {
		this.total_insurance_price = total_insurance_price;
	}
	
	

}
