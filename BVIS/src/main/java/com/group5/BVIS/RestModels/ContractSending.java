package com.group5.BVIS.RestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Implements REST model to send the contract in the form Capitol requires.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractSending {

	/**
	 * Constructor for ContractSending.
	 * @param customer_id, String. ID of associated customer.
	 * @param offer_id2, Integer. ID of selected insurance.
	 * @param contract, String. Text of associated contract.
	 */
	public ContractSending(String customer_id, int offer_id2, String contract) {
		super();
		this.customer_id = customer_id;
		this.offer_id = offer_id2;
		this.contract = contract;
	}

	public ContractSending() {
		
	}

	//Attributes
	
	private String customer_id;
	
	private int offer_id;
	
	private String contract;

	//Getter/Setter
	
	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public int getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(int offer_id) {
		this.offer_id = offer_id;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	/**
	 * Returns ContractSending as a String with its attributes.
	 */
	@Override
	public String toString() {
		return "ContractSending [customer_id=" + customer_id + ", offer_id=" + offer_id + ", contract=" + contract
				+ "]";
	}
	
	
	
}
