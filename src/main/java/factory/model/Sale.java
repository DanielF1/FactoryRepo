package factory.model;

import javax.persistence.Entity;

@Entity
public class Sale extends Department {
	
	/*
	 * Konstruktor
	 */
	@Deprecated
	public Sale(){}
	
	public Sale(String name) {
		
		super(name);
	}
}
