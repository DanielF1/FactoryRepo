package factory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Income beschreibt eine finanzielle Einnahme der Firma und wird im Rechnungswesen verwendet
 */
@Entity
public class Income {
	
	@Id 
	@GeneratedValue
	private  Long id;
	private String customer;
	private LocalDate date;
	private double value;
	private String sortOf;
	
	/** 
	 * Default Constructor
	 */
	@Deprecated
	public Income(){}
	
	/**
	 * Constructor
	 * 
	 * @param customer Person/Kunde der Einnahme
	 * @param date Datum der Einnahmen
	 * @param value Wert der Einnahmen
	 * @param sortOf Art der Einnahme
	 */
	public Income(String customer, LocalDate date, double value, String sortOf) {
		this.customer = customer;
		this.date = date;
		this.value = value;
		this.sortOf = sortOf;
	}

	/**
	 * getter
	 * @return customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * setter
	 * @param customer
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	/**
	 * getter
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * setter
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * getter
	 * @return value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * setter
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * getter
	 * @return sortOf
	 */
	public String getSortOf() {
		return sortOf;
	}

	/**
	 * setter
	 * @param sortOf
	 */
	public void setSortOf(String sortOf) {
		this.sortOf = sortOf;
	}
}
