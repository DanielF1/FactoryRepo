package factory.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Delivery {
	
	@Id 
	@GeneratedValue
	private Long id;
	private double amount;
	private Date date;
	private Long locationID;
	private String location;

	/*
	 * Konstruktor
	 */
	public Delivery(double amount, long dateInMillis, String location)
	{
		this.amount = amount;
		this.date = new Date(dateInMillis);
		this.location = location;
	
	}
	
	/*
	 * Konstruktor
	 */
	public Delivery(double amount, Date date, Long locationID)
	{
		this.amount = amount;
		this.date = date;
		this.locationID = locationID;
	
	}

	/*
	 * Konstruktor
	 */
	@Deprecated
	public Delivery(){}
	

	/*
	 * Getter und Setter
	 */
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getLocation() {
		return locationID;
	}

	public void setLocation(Long locationId) {
		this.locationID = locationID;
	}	
	
	public Long getId() {
		return id;
	}
}




