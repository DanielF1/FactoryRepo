package factory.model;

import javax.persistence.Entity;

@Entity
public class Accountancy extends Department {

	@Deprecated
	public Accountancy() {}

	public Accountancy(String name){
		
		super(name);
	}
}
