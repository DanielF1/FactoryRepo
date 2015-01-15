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
	 * 
	 * @param name Name der Produktion
	 * @param capacity Kapazität der Produktion
	 * @param stills Destillen der Produktion
	 */
	public Production(String name, double capacity, List<Still> stills) {
		super(name);
		this.capacity = capacity;
		this.stills = stills;
	}

	/**
	 * getter
	 * @return capacity
	 */
	public double getCapacity() {
		return capacity;
	}

	/**
	 * setter
	 * @param capacity
	 */
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	/**
	 * getter
	 * @return stills
	 */
	public List<Still> getStills() {
		return stills;
	}

	/**
	 * setter
	 * @param stills
	 */
	public void setStills(List<Still> stills) {
		this.stills = stills;
	}
}
