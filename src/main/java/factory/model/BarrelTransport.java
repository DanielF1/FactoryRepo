package factory.model;

import java.time.LocalDate;
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
	private List<Barrel> barrels;

	private LocalDate start_date;
	private LocalDate goal_date;
	private boolean arrived = false;
	
	/*
	 * constructor
	 */
	public BarrelTransport(List<Location> starting_point, List<Location> goal,
			List<Barrel> barrels, LocalDate start_date, LocalDate goal_date) {
		this.starting_point = starting_point;
		this.goal = goal;
		this.barrels = barrels;
		this.start_date = start_date;
		this.goal_date = goal_date;
	}

	BarrelTransport(){}
	
	
	/*
	 * getter & setter
	 */
	public List<Location> getStarting_point() {
		return starting_point;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getGoal_date() {
		return goal_date;
	}

	public void setGoal_date(LocalDate goal_date) {
		this.goal_date = goal_date;
	}

	public void setStarting_point(List<Location> starting_point) {
		this.starting_point = starting_point;
	}

	public List<Location> getGoal() {
		return goal;
	}

	public void setGoal(List<Location> goal) {
		this.goal = goal;
	}

	public List<Barrel> getBarrels() {
		return barrels;
	}

	public void setBarrels(List<Barrel> barrels) {
		this.barrels = barrels;
	}

	public boolean getArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}