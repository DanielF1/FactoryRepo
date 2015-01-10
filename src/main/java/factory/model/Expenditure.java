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
	
	
	/*
	 * Konstruktor
	 */
	public Expenditure(LocalDate date, double value, String sortOf) {
		this.date = date;
		this.value = value;
		this.sortOf = sortOf;
	}

	/*
	 * Getter und Setter
	 */
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
