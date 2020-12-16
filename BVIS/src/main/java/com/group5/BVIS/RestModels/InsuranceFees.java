package com.group5.BVIS.RestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Implements REST model to send the insurance fees.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InsuranceFees {
	
	public InsuranceFees() {}

	/**
	 * Constructor for InsuranceFees object.
	 * @param customer_id, String. ID of paying customer.
	 * @param money, double. Amount of money that is sent.
	 */
	public InsuranceFees(String customer_id, double money) {
		super();
		this.customer_id = customer_id;
		this.money = money;
	}

	//Attributes
	private String customer_id;
	
	private double money;

	//Getter/Setter
	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	/**
	 * Returns the InsuranceFees object and its attributes as a String.
	 */
	@Override
	public String toString() {
		return "InsuranceFees [customer_id=" + customer_id + ", money=" + money + "]";
	}
}
