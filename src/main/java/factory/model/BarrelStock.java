package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class BarrelStock extends Department {
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Barrel> barrels;
	
	@Deprecated
	public BarrelStock() {}
	
	public BarrelStock(String name, List<Barrel> mapBarrels){
		
		super(name);
		this.barrels = mapBarrels;
	}
	
	public List<Barrel> getBarrels() {
		return barrels;
	}

	public void setBarrels(List<Barrel> barrels) {
		this.barrels = barrels;
	}
}
