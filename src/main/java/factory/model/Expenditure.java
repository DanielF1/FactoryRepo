package factory.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Expenditure {
	
	@Id 
	@GeneratedValue
	private  Long id;
	private LocalDate date;
	private double value;
	private String sortOf;
		

	/**
	 * Default Constructor
	 */
	@Deprecated
	public Expenditure(){}


	
	/**
	 * Construktor
	 * 
	 * @param date Datum der Ausgaben
	 * @param value Wert der Ausgaben
	 * @param sortOf Art der Ausgaben
	 */
	public Expenditure(LocalDate date, double value, String sortOf) {
		this.date = date;
		this.value = value;
		this.sortOf = sortOf;
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
