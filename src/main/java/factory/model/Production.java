package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Production extends Department {

	private double capacity;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Still> stills;

	@Deprecated
	public Production(){}
	
	public Production(String name, double capacity, List<Still> stills) {
		super(name);
		this.capacity = capacity;
		this.stills = stills;
	}

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
