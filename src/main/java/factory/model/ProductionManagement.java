package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ProductionManagement {

	@Id @GeneratedValue private Long id;
	private String location_name;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ProductionADay> production_a_day;

	/**
	 * Constructor
	 */
	public ProductionManagement(String location_name,
			List<ProductionADay> production_a_day) {
		super();
		this.location_name = location_name;
		this.production_a_day = production_a_day;
	}

	
	/**
	 * default Constructor
	 */
	public ProductionManagement(){}

	
	/**
	 * Getter and Setter
	 * 
	 * @return necessary value
	 */
	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public List<ProductionADay> getProduction_a_day() {
		return production_a_day;
	}

	public void setProduction_a_day(List<ProductionADay> production_a_day) {
		this.production_a_day = production_a_day;
	}
	
	
	
}
