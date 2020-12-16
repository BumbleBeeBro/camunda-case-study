package com.group5.BVIS.webapp.formModels;


import javax.validation.constraints.NotNull;

/**
 * Represents the html input form as a Java class.
 * Deprecated because billing is now sent directly via eMail.
 */
@Deprecated
public class GetBilling {
	

	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "GetBilling [customerId=" + customerId + "]";
	}


	
}
