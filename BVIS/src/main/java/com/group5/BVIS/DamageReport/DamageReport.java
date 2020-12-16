package com.group5.BVIS.DamageReport;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.group5.BVIS.Contracts.Contract;

/**
 * Class to model a DamageReport object. DamageReport entity.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DamageReport implements java.io.Serializable {
	
	/**
	 * Constructor for a DamageReport object.
	 * @param customerId, String. ID of associated customer.
	 * @param contract, Contract. Associated contract.
	 * @param damageClassification, Integer. Classifier for the extend of the damager (1-3).
	 */
	public DamageReport(@NotNull String customerId, @NotNull Contract contract,
			@NotNull @Min(1) @Max(3) int damageClassification) {
		super();
		this.customerId = customerId;
		this.contract = contract;
		this.damageClassification = damageClassification;
	}

	private static final long serialVersionUID = 42L;
	
	//Attributes
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String customerId;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER, optional = true)
	private Contract contract;
	
	@NotNull
	@Min(1)
	@Max(3)
	private int damageClassification;
	
	//for JPA
	public DamageReport() {}

	public Long getId() {
		return id;
	}

	//Getter/Setter
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public int getDamageClassification() {
		return damageClassification;
	}

	public void setDamageClassification(int damageClassification) {
		this.damageClassification = damageClassification;
	}

	/**
	 * Converts DamageReport object to a String for logging.
	 * @return String of DamageReport and its attributes.
	 */
	@Override
	public String toString() {
		return "DamageReport [id=" + id + ", customerId=" + customerId + ", contract=" + contract
				+ ", damageClassification=" + damageClassification + "]";
	}
	

}
