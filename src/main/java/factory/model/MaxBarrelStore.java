package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MaxBarrelStore {
	
	private @Id @GeneratedValue Long id;
	private String quality;
	private int age;
	private double amount;


	/**
	 * Constructor
	 * 
	 * @param quality Qualität des Maximalen FassLagers
	 * @param age Alter des Maximalen FassLagers
	 * @param amount Menge des Maximalen FassLagers
	 */
	public MaxBarrelStore(String quality,int age, double amount) 
	{
		this.quality = quality;
		this.age = age;
		this.amount = amount;
	}

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
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
