package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bottle {
	private @Id @GeneratedValue Long id;
	private double amount;
	
	/*
	 * Konstruktor
	 */
	public Bottle(double amount){
		this.amount = amount;
	}
	
	Bottle(){}
	
	/*
	 * Getter & Setter
	 */
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public double getAmount() 
	{
		return amount;
	}
	
	public void setAmount(double amount) 
	{
		this.amount = amount;
	}
}
