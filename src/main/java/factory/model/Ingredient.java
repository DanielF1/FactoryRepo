package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ingredient {

	private @Id @GeneratedValue Long id;
	private String quality;
	private int age;
	private double amount;
	private String unit;
	
	/**
	 * Constructor
	 * 
	 * @param quality Qualität der Zutaten
	 * @param age Alter der Zutaten
	 * @param amount Menge der Zutaten
	 * @param unit Anzahl der Zutaten
	 */
	public Ingredient(String quality, int age, double amount, String unit) {
		this.quality = quality;
		this.age = age;
		this.amount = amount;
		this.unit = unit;
	}

	/**
	 * default Constructor
	 */
	public Ingredient(){}

	
	/**
	 * getter
	 * @return quality
	 */
	public String getQuality() {
		return quality;
	}

	/**
	 * setter
	 * @param quality
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * getter
	 * @return age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * setter
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * getter
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * setter
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * getter
	 * @return unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * setter
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
