package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.springframework.stereotype.Component;


/**
 * BarrelStock ist ein Department, in dem die leeren als auch die vollen Fässer gelagert werden,
 * die während der Produktion benötigt werden
 */

@Entity
@Component
public class BarrelStock extends Department {
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Barrel> barrels;
	
	/** 
	 * Default Constructor
	 */
	@Deprecated
	public BarrelStock(){}

	
	/**
	 * Constructor
	 * 
	 * @param name Name des Fasslagers
	 * @param mapBarrels Liste des Fasslagers
	 */
	public BarrelStock(String name, List<Barrel> mapBarrels){
		
		super(name);
		this.barrels = mapBarrels;
	}


	/**
	 * getter
	 * @return barrels
	 */
	public List<Barrel> getBarrels() {
		return barrels;
	}

	/**
	 *setter
	 * @param barrels
	 */
	public void setBarrels(List<Barrel> barrels) {
		this.barrels = barrels;
	}
}
