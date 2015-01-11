package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class BottleStock extends Department{

	@OneToMany(cascade = CascadeType.ALL)
	private List<Bottle> bottles;	

	/* 
	 * default Konstruktor
	 */
	@Deprecated
	public BottleStock(){}
	
	/*
	 * Konstruktor
	 */
	public BottleStock(String name, List<Bottle> bottles/*, List<Bottle> fullbottles*/){
		
		super(name);
		this.bottles = bottles;
		
	}
	/*
	 * Getter und Setter
	 */
	public List<Bottle> getBottles() {
		return bottles;
	}

	public void setBottles(List<Bottle> bottles) {
		this.bottles = bottles;
	}


	
}
