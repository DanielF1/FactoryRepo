package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ingredient {

	private @Id @GeneratedValue Long id;
	private String quality;
	private int age;
	private int amount;
	private String unit;
	
	public Ingredient(String quality, int age, int amount, String unit) {
		this.quality = quality;
		this.age = age;
		this.amount = amount;
		this.unit = unit;
	}

	public Ingredient(){}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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

	
	/*
	 * Getter & Setter
	 */

}
