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
	 * 
	 * @param name Name der Flasche
	 * @param amount Menge der Flasche
	 * @param price Preis der Flasche
	 */
	public Bottle(String name, double amount, double price)
	{
		this.name = name;
		this.amount = amount;
		this.price = price;
	}
	
	/**
	 * Getter and Setter
	 * 
	 * Getter
	 * @return id
	 */
	public Long getId() 
	{
		return id;
	}
	
	/**
	 * getter
	 * @return amount
	 */
	public double getAmount() 
	{
		return amount;
	}
	
	/**
	 * setter
	 * @param amount
	 */
	public void setAmount(double amount) 
	{
		this.amount = amount;
	}
	
	/**
	 * getter
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getter
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * setter
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
}
