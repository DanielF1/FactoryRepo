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
	 * 
	 * @param name Name der Abteilung
	 */
	public Department(String name) {
		this.name = name;
	}
	

	/**
	 * getter
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getter
	 * @return id
	 */
	public Long getId() {
		return id;
	}
}
