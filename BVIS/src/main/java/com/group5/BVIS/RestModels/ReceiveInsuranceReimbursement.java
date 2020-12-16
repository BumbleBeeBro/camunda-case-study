package com.group5.BVIS.RestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Implements REST model to receive and interpret the reimbursement from Capitol.
 */
@Deprecated
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveInsuranceReimbursement {

	//Attributes.
	private String customer_id;
	
	private String list_of_damages;
	
	private double money;
	
	//JPA
	public ReceiveInsuranceReimbursement() {}

	/**
	 * Constructor for ReceiveInsuranceReimbursement.
	 * @param customer_id, String. ID of the associated customer.
	 * @param list_of_damages, String. A textual list of damage associated with a certain customer.
	 * @param money, double. Amount of money reimbursed.
	 */
	public ReceiveInsuranceReimbursement(String customer_id, String list_of_damages, double money) {
		super();
		this.customer_id = customer_id;
		this.list_of_damages = list_of_damages;
		this.money = money;
	}

	//Getter/Setter.
	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getList_of_damages() {
		return list_of_damages;
	}

	public void setList_of_damages(String list_of_damages) {
		this.list_of_damages = list_of_damages;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
	
	/**
	 * Returns object and the values of its attributes as a String.
	 */
	@Override
	public String toString() {
		return "ReceiveInsuranceReimbursement [customer_id=" + customer_id + ", list_of_damages=" + list_of_damages
				+ ", money=" + money + "]";
	}
	
	
}
