package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bottle {
	private @Id @GeneratedValue Long id;
	private double amount;
	private int quantity;
	private BottleType type;
	
	public static enum BottleType 
	{
		EMPTY, FULL;
	}
	
	/*
	 * Konstruktor
	 */
	public Bottle(double amount, BottleType type){
		this.amount = amount;
//		this.quantity = quantity;
		this.type = type;
	}
	
	Bottle(){}
	
	/*
	 * Getter & Setter
	 */
	
	public BottleType getType() 
	{
		return type;
	}

	public void setType(BottleType type) 
	{
		this.type = type;
	}
	
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
