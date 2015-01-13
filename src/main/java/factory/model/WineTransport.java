package factory.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class WineTransport {

	private @Id @GeneratedValue Long id;

	private String starting_point;
	private String goal;
	
	private double amount;
	private LocalDateTime start_date;
	private LocalDateTime goal_date;
	private boolean arrived = false;
	
	public WineTransport(String start, String goal,
			double amount, LocalDateTime start_date, LocalDateTime goal_date,
			boolean arrived) {
		
		this.starting_point = start;
		this.goal = goal;
		this.amount = amount;
		this.start_date = start_date;
		this.goal_date = goal_date;
		this.arrived = arrived;
	}

	public WineTransport(){}
	
	
	/*
	 * getter & setter
	 */
	public String getStarting_point() {
		return starting_point;
	}

	public void setStarting_point(String starting_point) {
		this.starting_point = starting_point;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDateTime start_date) {
		this.start_date = start_date;
	}

	public LocalDateTime getGoal_date() {
		return goal_date;
	}

	public void setGoal_date(LocalDateTime goal_date) {
		this.goal_date = goal_date;
	}

	public boolean isArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}
}
