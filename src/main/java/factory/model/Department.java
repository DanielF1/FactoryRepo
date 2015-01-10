package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Department {

	@Id 
	@GeneratedValue
	private Long id;
	private String name;

	/*
	 * Konstruktor
	 */
	@Deprecated
	public Department(){}

	public Department(String name) {
		this.name = name;
	}
	
	/*
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

