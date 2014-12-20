package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Still {

	private @Id @GeneratedValue Long id;
	private int amount;
	private boolean status_one;
	private boolean status_two;
	
	public Still(int amount, boolean status_one, boolean status_two)
	{
		this.amount = amount;
		this.status_one = status_one;
		this.status_two = status_two;
	}

	@Deprecated
	public Still(){}
	
	
	/*
	 * getter & setter
	 */
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean getStatus_one() {
		return status_one;
	}

	public void setStatus_one(boolean status_one) {
		this.status_one = status_one;
	}

	public boolean getStatus_two() {
		return status_two;
	}

	public void setStatus_two(boolean status_two) {
		this.status_two = status_two;
	}	
}
