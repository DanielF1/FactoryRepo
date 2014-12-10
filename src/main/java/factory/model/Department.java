package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Department {

	@Id 
	@GeneratedValue
	private Long id;
	private String name;
	private double quantity;
	private double capacity;
	
	@Deprecated
	public Department(){}

	public Department(String name) {
		this.name = name;
	}
	
	public Department(String name, double quantity, double capacity) {
		this.name = name;
		this.quantity = quantity;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}
	
	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
}
