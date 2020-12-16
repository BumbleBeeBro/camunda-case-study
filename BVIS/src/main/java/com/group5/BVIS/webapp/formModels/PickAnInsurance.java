package com.group5.BVIS.webapp.formModels;


import javax.validation.constraints.NotNull;

/**
 * Represents the html input form as a Java class.
 * PickAnInsurance View.
 */
public class PickAnInsurance {

	//ID of selected insurance offer.
	@NotNull(message = "Please choose an insurance")
	private int offerId;

	//Setter/Getter
	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	/**
	 * Returns ID of selected insurance as as String.
	 */
	@Override
	public String toString() {
		return "PickAnInsurance [offerId=" + offerId + "]";
	}
	
	
	
	
	
}
