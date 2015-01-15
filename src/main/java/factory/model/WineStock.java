package factory.model;

import javax.persistence.Entity;

/**
 * WineStock ist ein Department, welches das Weinlager darstellt, in das der Rohstoff für die Produktion transportiert wird
 */
@Entity
public class WineStock extends Department {
	
	private double amount;
	
	/** 
	 * Default Constructor
	 */
	@Deprecated
	public WineStock() {}
	
	/**
	 *  Constructor
	 * 
	 * @param name Name des Weinlagers 
	 * @param amount Menge des Weinlagers
	 */
	public WineStock(String name, double amount) 
	{
		super(name);
		this.amount = amount;
	}

	/**
	 * getter 
	 * @return  amount
	 */ 
	public double getAmount() {
		return amount;
	}

	/**
	 * setter
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
