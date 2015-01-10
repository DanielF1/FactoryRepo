package factory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Income {
	
	@Id 
	@GeneratedValue
	private  Long id;
	private String customer;
	private LocalDate date;
	private double value;
	private String sortOf;
	
	/*
	 * leerer Konstruktor
	 */
	@Deprecated
	public Income(){}
	
	/*
	 * Konstruktor
	 */
	public Income(String customer, LocalDate date, double value, String sortOf) {
		this.customer = customer;
		this.date = date;
		this.value = value;
		this.sortOf = sortOf;
	}

	/*
	 * Getter und Setter
	 */
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getSortOf() {
		return sortOf;
	}

	public void setSortOf(String sortOf) {
		this.sortOf = sortOf;
	}
}
