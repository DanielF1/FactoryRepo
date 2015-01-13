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
	
	
	public MaxBarrelStore(String quality,int age, double amount) 
	{
		this.quality = quality;
		this.age = age;
		this.amount = amount;
	}


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


	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
