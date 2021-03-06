package factory.model;

import javax.persistence.Entity;

/**
 * Sale ist ein Department, was die Verkaufstelle der Firma darstellt
 */
@Entity
public class Sale extends Department {
	
	/** 
	 * Default Constructor
	 */
	@Deprecated
	public Sale(){}

	/**
	 * Constructor
	 * 
	 * @param name Name des Verkaufs
	 */
	public Sale(String name) {
		
		super(name);
	}
}