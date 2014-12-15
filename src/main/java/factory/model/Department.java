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
	private double quantity; //Wert gibt an, wie viele Einheiten sich im Lager befinden
//	private double capacity; //Wert gibt an, wie viel Produktionskapazität noch vorhanden ist

	@Deprecated
	public Department(){}

	public Department(String name, double capacity) {
		this.name = name;
//		this.capacity = capacity;
	}
	
	public Department(String name, double quantity, double capacity) {
		this.name = name;
		this.quantity = quantity;
//		this.capacity = capacity;
	}
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
//	public double getCapacity() {
//		return capacity;
//	}
//
//	public void setCapacity(double capacity) {
//		this.capacity = capacity;
//	}
	
	public Department(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
}
