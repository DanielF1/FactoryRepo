package factory.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class BarrelTransport {

	private @Id @GeneratedValue Long id;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Location> starting_point;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Location> goal;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<BarrelForTransport> barrels;

	private LocalDateTime start_date;
	private LocalDateTime goal_date;
	private boolean arrived = false;
	
	/** 
	 * Default Constructor
	 */
	@Deprecated
	public BarrelTransport(){}
	


	/**
	 * Constructor
	 * 
	 * @param starting_point Startpunkt des Barrelstransports
	 * @param goal Ziel des Barrelstransports
	 * @param barrels Fass des Barrelstransports
	 * @param start_date Startdatum des Barrelstransports
	 * @param goal_date Zielpunkt des Barrelstransports
	 */
	public BarrelTransport(List<Location> starting_point, List<Location> goal,
			List<BarrelForTransport> barrels, LocalDateTime start_date, LocalDateTime goal_date) {
		this.starting_point = starting_point;
		this.goal = goal;
		this.barrels = barrels;
		this.start_date = start_date;
		this.goal_date = goal_date;
	}

	/**
	 * getter
	 * @return starting_point
	 */
	public List<Location> getStarting_point() {
		return starting_point;
	}
	
	/**
	 * getter
	 * @return start_date
	 */
	public LocalDateTime getStart_date() {
		return start_date;
	}
	
	/**
	 * setter
	 * @param start_date
	 */
	public void setStart_date(LocalDateTime start_date) {
		this.start_date = start_date;
	}
	
	/**
	 * getter
	 * @return goal_date
	 */
	public LocalDateTime getGoal_date() {
		return goal_date;
	}
	
	/**
	 * setter
	 * @param goal_date
	 */
	public void setGoal_date(LocalDateTime goal_date) {
		this.goal_date = goal_date;
	}

	/**
	 * setter
	 * @param starting_point
	 */
	public void setStarting_point(List<Location> starting_point) {
		this.starting_point = starting_point;
	}
	
	/**
	 * getter
	 * @return goal
	 */
	public List<Location> getGoal() {
		return goal;
	}

	/**
	 * setter
	 * @param goal
	 */
	public void setGoal(List<Location> goal) {
		this.goal = goal;
	}
	
	/**
	 * getter
	 * @return barrels
	 */
	public List<BarrelForTransport> getBarrels() {
		return barrels;
	}
	
	/**
	 * setter
	 * @param barrels
	 */
	public void setBarrels(List<BarrelForTransport> barrels) {
		this.barrels = barrels;
	}
	
	/**
	 * getter
	 * @return arrived
	 */
	public boolean getArrived() {
		return arrived;
	}

	/**
	 * setter
	 * @param arrived
	 */
	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	/**
	 * getter
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * setter
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
}