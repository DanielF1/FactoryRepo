package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * BottleStock ist ein Department, in welchem die Flaschen gelagert werden, 
 * die für die Produktion notwendig sind und für die Flaschen, die verkaufsbereit sind
 */
@Entity
public class BottleStock extends Department{

	@OneToMany(cascade = CascadeType.ALL)
	private List<Bottle> bottles;	

	/** 
	 * Default Constructor
	 */
	@Deprecated
	public BottleStock(){}
	
	
	/**
	 * Constructor
	 * 
	 * @param name Name des Flaschenlagers
	 * @param bottles Flaschen des Flaschenlagers
	 */
	public BottleStock(String name, List<Bottle> bottles/*, List<Bottle> fullbottles*/){
		
		super(name);
		this.bottles = bottles;
		
	}
	
	/**
	 * getter
	 * @return bottles
	 */
	public List<Bottle> getBottles() {
		return bottles;
	}
	
	/**
	 * setter
	 * @param bottles
	 */
	public void setBottles(List<Bottle> bottles) {
		this.bottles = bottles;
	}
	
}
