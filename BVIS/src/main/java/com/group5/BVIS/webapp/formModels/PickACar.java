package com.group5.BVIS.webapp.formModels;


import javax.validation.constraints.NotNull;

/**
 * Represents the html input form as a Java class.
 * PickACar View.
 */
public class PickACar {
	
	//ID of chosen vehicle.
	@NotNull(message = "Please choose a car")
	private Long id;
	
	//Getter/Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returns the ID of the vehicle selected in the form.
	 */
	@Override
	public String toString() {
		return "CarFormModel: " + id;
	}


	
}
