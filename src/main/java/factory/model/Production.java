package factory.model;

import javax.persistence.Entity;

@Entity
public class Production extends Department {

	private double capacity;
	
	@Deprecated
	public Production(){}
	
	public Production(String name, double capacity) {
		
		super(name);
		this.capacity = capacity;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
}
