package com.group5.BVIS.RestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Implements REST model to receive and interpret the OverallDamageAssessment by Capitol.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OverallDamageAssessment {

	//Attributes.
	@JsonProperty("final_Payment")
	private double final_payment;
	
	@JsonProperty("claim_Fee")
	private double claim_fee;
	
	@JsonProperty("damage_Amount")
	private double damage_amount;
	
	/**
	 * Constructor for OverallDamageAssessment.
	 * @param final_payment, double. Share of the damage that needs to be paid by the customer.
	 * @param claim_fee, double. Insurance fees due to claims. Already included in final_payment.
	 * @param damage_amount, double. Overall financial damage. Includes final_payment, claim_fee and the part paid by the insurance.
	 */
		public OverallDamageAssessment(double final_payment, double claim_fee, double damage_amount) {
			super();
			this.final_payment = final_payment;
			this.claim_fee = claim_fee;
			this.damage_amount = damage_amount;
		}
	
	//JPA
	public OverallDamageAssessment() {}

	public double getFinal_payment() {
		return final_payment;
	}

	public void setFinal_payment(double final_payment) {
		this.final_payment = final_payment;
	}

	public double getClaim_fee() {
		return claim_fee;
	}

	public void setClaim_fee(double claim_fee) {
		this.claim_fee = claim_fee;
	}

	public double getDamage_amount() {
		return damage_amount;
	}

	public void setDamage_amount(double damage_amount) {
		this.damage_amount = damage_amount;
	}



	
	
}
