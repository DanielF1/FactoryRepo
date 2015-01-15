package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


/**
 * Production ist ein Department, in der die Herstellung des Produkts der Firma stattfindet
 */

@Entity
public class Production extends Department {

	private double capacity;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Still> stills;

	/** 
	 * Default Constructor
	 */
	@Deprecated
	public Production(){}
	
	/**
	 * Constructor
	 */
	public Production(String name, double capacity, List<Still> stills) {
		super(name);
		this.capacity = capacity;
		this.stills = stills;
	}

	/**
	 * Getter and Setter
	 */
	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public List<Still> getStills() {
		return stills;
	}

	public void setStills(List<Still> stills) {
		this.stills = stills;
	}
}
