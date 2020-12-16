package com.group5.BVIS.RestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Implements REST model to signal the end of a contract to Capitol.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignalContractEnd {
	

	public SignalContractEnd() {
	}

	/**
	 * Constructor for SignalContractEnd.
	 * @param customer_id, String. ID of the customer.
	 */
	public SignalContractEnd(String customer_id) {
		super();
		this.customer_id = customer_id;
	}

	//Attributes
	private String customer_id;

	//Getter/Setter
	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
}
