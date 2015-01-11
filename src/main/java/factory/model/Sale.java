package factory.model;

import javax.persistence.Entity;

@Entity
public class Sale extends Department {
	
	/* 
	 * default Konstruktor
	 */
	@Deprecated
	public Sale(){}
	
	/* 
	 * Konstruktor
	 */
	public Sale(String name) {
		
		super(name);
	}
}
