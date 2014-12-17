package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Production extends Department {

	private double capacity;
	
	@OneToMany(cascade = CascadeType.ALL)
	private static List<Still> stills;
	
	@Deprecated
	public Production(){}
	
	public Production(String name, List<Still> stillMap) {
		
		super(name);
		this.capacity = capacity;
		this.stills = stillMap;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public static List<Still> getStills() {
		return stills;
	}

	public static void setStills(List<Still> stills) {
		Production.stills = stills;
	}
	
	
}
