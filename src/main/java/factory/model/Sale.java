package factory.model;

import javax.persistence.Entity;

@Entity
public class Sale extends Department {
	
	@Deprecated
	public Sale(){}
	
	public Sale(String name) {
		
		super(name);
	}
}
