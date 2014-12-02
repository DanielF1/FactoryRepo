package videoshop.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BarrelStock {
	
	private @Id @GeneratedValue Long id;
	private String name;
	private String distillate;
	private int amount;
	
	@OneToMany(cascade = CascadeType.ALL)
	private  List<Barrel> barrels = new ArrayList<Barrel>();
	
	public BarrelStock(String name, List<Barrel> mapBarrels){
		this.name = name;
		this.barrels = mapBarrels;

	}
	
	public BarrelStock(){}
	
	public String getName(){
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Barrel> getBarrels() {
		return barrels;
	}

	public void setBarrels(List<Barrel> barrels) {
		this.barrels = barrels;
	}

	public void setName(String name) {
		this.name = name;
	}
	
//	public String getDistillate(){
//		return distillate;
//	}
//	
//	public int getAmount(){
//		return amount;
//	}
	
}
