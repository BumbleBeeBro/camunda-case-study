package com.group5.BVIS.Billings;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.group5.BVIS.Contracts.Contract;




/**
 * Class to model a Billing object. Billing entity.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Billing implements java.io.Serializable {
	
	/**
	 * Constructor for a Billing object.
	 * @param contract, Contract. A Contract object that should be billed.
	 * @param damageCharges, double. A number of damage charges related to a contract that are billed.
	 */
	public Billing( @NotNull Contract contract, @NotNull double damageCharges) {
		super();
		this.contract = contract;
		this.damageCharges = Math.round(damageCharges * 100.0) / 100.0;
		this.priceToPay = Math.round((contract.getTotal_price() + this.damageCharges) * 100.0) / 100.0;
	}

	private static final long serialVersionUID = 42L;
	
	//for JPA
	public Billing() {	
		
	}

	//Attributes
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER, optional = true)
	private Contract contract;
	
	@NotNull
	private double damageCharges;
	
	@NotNull
	private double priceToPay;

	//Getter/Setter
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public double getDamageCharges() {
		return damageCharges;
	}

	public void setDamageCharges(double damageCharges) {
		this.damageCharges = damageCharges;
	}

	public double getPriceToPay() {
		return priceToPay;
	}

	public void setPriceToPay(double priceToPay) {
		this.priceToPay = priceToPay;
	}

	/**
	 * Method to display a Billing and its attributes for logging
	 * @return String of Variables.
	 */
	public String toVariableString() {
		return "Billing [id=" + id + ", contract=" + contract + ", damageCharges=" + damageCharges + ", priceToPay="
				+ priceToPay + "]";
	}
	
	/**
	 * Method to display a Billing object in a textual structure for mailing.
	 * @return String of a Billing in a textual structure.
	 */
	@Override
	public String toString() { 
		return 	"Billing: " + id + " for contract: "+ contract.getId() + "_"+ contract.getCustomer_type() + "\r\n" +
				"----------------------------------------------" + "\r\n" +
				"Name:                          " + contract.getName() + "\r\n" +
				"Address:                       " + contract.getAddress() + "\r\n" +
				"----------------------------------------------" + "\r\n" +
				"Price according to contract:  €"+  contract.getTotal_price() + "\r\n" +
				"Additional insurance charges: €" + damageCharges + "\r\n" +
				"----------------------------------------------" + "\r\n" +
				"Overall                       €" + priceToPay + "\r\n" +
				"----------------------------------------------" + "\r\n" +
				"BVIS Ltd. \r\n"+ 
				"or download it here: http://localhost:8080/billings/" + id + "/file";
	}
	


	
	

}
