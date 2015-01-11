package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Ein Department ist ein Bestandteil einer Location
 */

@Entity
public class Department {

	@Id 
	@GeneratedValue
	private Long id;
	private String name;

	/**
	 * Default Constructor
	 */
	@Deprecated
	public Department(){}

	/** 
	 * Constructor
	 */
	public Department(String name) {
		this.name = name;
	}
	
	/**
	 * Getter und Setter
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

}

