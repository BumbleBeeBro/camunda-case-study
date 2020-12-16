package com.group5.BVIS.RestModels;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import spinjar.com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Implements REST model to receive and interpret an insurance offer from Capitol.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InsuranceOffering implements java.io.Serializable {
	
	private static final long serialVersionUID = 42L;
	
	/**
	 * Constructor for InsuranceOffering.
	 * @param offer_id, Long. ID of the offering.
	 * @param name, String. The name of the offering.
	 * @param price, float.  price per day of the insurance offering.
	 * @param description, String. A text describing the services of the insurance.
	 */
	public InsuranceOffering(Long offer_id, String name, float price, String description) {
		super();
		this.offer_id = offer_id;
		this.name = name;
		this.price = price;
		this.Description = description;
	}
	
	//for JPA
	public InsuranceOffering() {
	}
	
	//Attributes
	@NotNull
	@JsonProperty("offer_Id")
	private Long offer_id;
	
	@NotNull
	@JsonProperty("name")
	private String name;

	@NotNull
	@JsonProperty("price")
	private float price;	
	
	@NotNull
	@JsonProperty("description")
	private String Description;

	//Getter/Setter
	public Long getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(Long offer_id) {
		this.offer_id = offer_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * Returns the InsuranceOffering and its attributes as a String.
	 */
	@Override
	public String toString() {
		return "InsuranceOffering [offer_id=" + offer_id + ", name=" + name + ", price=" + price + ", Description="
				+ Description + "]";
	}
}
