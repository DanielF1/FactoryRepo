package factory.model;

import javax.persistence.Entity;

@Entity
public class WineStock extends Department {
	
	private double amount;
	
	@Deprecated
	public WineStock() {}
	
	public WineStock(String name, double amount) {
	
		super(name);
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
