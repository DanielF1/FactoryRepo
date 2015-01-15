package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bottle {
	
	private @Id @GeneratedValue Long id;
	private String name;
	private double amount;
	private double price;
	
	/** 
	 * Default Constructor
	 */
	@Deprecated
	public Bottle(){}
	
	/**
	 * Constructor
	 */
	public Bottle(String name, double amount, double price)
	{
		this.name = name;
		this.amount = amount;
		this.price = price;
	}
	
	/**
	 * Getter and Setter
	 */
	public Long getId() 
	{
		return id;
	}
	
	public double getAmount() 
	{
		return amount;
	}
	
	public void setAmount(double amount) 
	{
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
