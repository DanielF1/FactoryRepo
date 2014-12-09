package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MaxStore {
	
	private @Id @GeneratedValue Long id;
	private String content;
	private double amount;
	
	
	public MaxStore(String content, double amount) 
	{
		this.content = content;
		this.amount = amount;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
