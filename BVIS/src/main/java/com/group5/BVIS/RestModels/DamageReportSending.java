package com.group5.BVIS.RestModels;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Implements REST model to send the damage report, in the form that Capitol requires.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DamageReportSending {

	/**
	 * Constructor for a DamageReportSending object.
	 * @param customer_id, String. ID of associated customer.
	 * @param damage_classification, Integer. Category of damage. At least 1, at maximun 3.
	 */
	public DamageReportSending(String customer_id, @Min(1) @Max(3) int damage_classification) {
		super();
		this.customer_id = customer_id;
		this.damage_classification = damage_classification;
	}

	public DamageReportSending() {
	}

	//Attributes
	private String customer_id;
	
	@Min(1)
	@Max(3)
	private int damage_classification;

	//Getter/Setter
	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public int getDamage_classification() {
		return damage_classification;
	}

	public void setDamage_classification(int damage_classification) {
		this.damage_classification = damage_classification;
	}

	/**
	 * Returns object and its attributes as a String.
	 */
	@Override
	public String toString() {
		return "DamageReportSending [customer_id=" + customer_id + ", damage_classification=" + damage_classification
				+ "]";
	}
}
