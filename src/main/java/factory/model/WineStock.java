package factory.model;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Entity
@Component
public class WineStock extends Department {
	
	private double amount;
	
	/* 
	 * default Konstruktor
	 */
	@Deprecated
	public WineStock() {}
	
	/*
	 * Konstruktor
	 */
	public WineStock(String name, double amount) 
	{
		super(name);
		this.amount = amount;
	}

	/*
	 * KGetter und Setter
	 */
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
