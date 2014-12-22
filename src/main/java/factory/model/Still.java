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
	private boolean timer_stop;
	
	public Still(int amount, boolean status_one, boolean status_two, boolean timer_stop)
	{
		this.amount = amount;
		this.status_one = status_one;
		this.status_two = status_two;
		this.timer_stop = timer_stop;
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

	public boolean isTimer_stop() {
		return timer_stop;
	}

	public void setTimer_stop(boolean timer_stop) {
		this.timer_stop = timer_stop;
	}
	
	
}
