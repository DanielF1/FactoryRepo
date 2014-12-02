package videoshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ingredient {

	private @Id @GeneratedValue Long id;
	private String name;
	private int amount;
	private String unit;
	
	/*
	 * Konstruktor
	 */
	public Ingredient(String name, int amount, String unit){
		this.name = name;
		this.amount = amount;
		this.unit = unit;
	}
	

	public Ingredient(){}

	
	/*
	 * Getter & Setter
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
