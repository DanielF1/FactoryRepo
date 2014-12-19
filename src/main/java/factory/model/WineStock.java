package factory.model;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Entity
@Component
public class WineStock extends Department {
	
	private double amount;
	
	@Deprecated
	public WineStock() {}
	
	public WineStock(String name, double amount) 
	{
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
