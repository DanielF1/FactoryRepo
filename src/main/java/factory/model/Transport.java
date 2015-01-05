package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Transport {

	@Id 
	@GeneratedValue
	private Long id;
	private String sort;
	private double quantity;
	private Long source;
	private Long destination;

	public Transport(){}
	
	public Transport(String sort, double quantity, Long source, Long destination) {
		this.sort = sort;
		this.source = source;
		this.destination = destination;
		this.quantity = quantity;
	}

	

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getDestination() {
		return destination;
	}

	public double getQuantity() {
		return quantity;
	}

	public Long getSource() {
		return source;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public void setSource(Long source) {
		this.source = source;
	}

	public void setDestination(Long destination) {
		this.destination = destination;
	}
}
