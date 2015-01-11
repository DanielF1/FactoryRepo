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
	 * default Konstruktor
	 */
	@Deprecated
	public Department(){}

	/* 
	 * Konstruktor
	 */
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

