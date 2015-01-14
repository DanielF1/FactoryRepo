package factory.model;

import javax.persistence.Entity;

/**
 * Accountancy ist ein Department, das die Verwaltung und das Rechnungswesen der Firma darstellt
 */
@Entity
public class Accountancy extends Department {

	/**
	 * Default Constructor
	 */
	@Deprecated
	public Accountancy() {}

	
	/**
	 * Constructor
	 * @param name
	 */
	public Accountancy(String name){
		super(name);
	}
}
