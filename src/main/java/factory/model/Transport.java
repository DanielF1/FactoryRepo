package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Transport {

	@Id 
	@GeneratedValue
	private Long id;
	private int quantity;
	private Location source;
	private Location destination;
	
	public Transport(){}
	
	public Transport(Location source, Location destination, int quantity) {
		this.source = source;
		this.destination = destination;
		this.quantity = quantity;
	}

	

	public Location getDestination() {
		return destination;
	}

	public int getQuantity() {
		return quantity;
	}

	public Location getSource() {
		return source;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setSource(Location source) {
		this.source = source;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}
}
