package factory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductionMonth {

	private @Id @GeneratedValue Long id;
	private String name;
	private int length;
	double processing_through_stills;
	double wine_delivery_amount;
	double wine_transport_amount;
	double max_winestock_amount;
	
	public ProductionMonth(String name, int length,
			double processing_through_stills, double wine_delivery_amount,
			double wine_transport_amount, double max_winestock_amount) {

		this.name = name;
		this.length = length;
		this.processing_through_stills = processing_through_stills;
		this.wine_delivery_amount = wine_delivery_amount;
		this.wine_transport_amount = wine_transport_amount;
		this.max_winestock_amount = max_winestock_amount;
	}

	public ProductionMonth(){}
	
	
	/*
	 * getter & setter
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getProcessing_through_stills() {
		return processing_through_stills;
	}

	public void setProcessing_through_stills(double processing_through_stills) {
		this.processing_through_stills = processing_through_stills;
	}

	public double getWine_delivery_amount() {
		return wine_delivery_amount;
	}

	public void setWine_delivery_amount(double wine_delivery_amount) {
		this.wine_delivery_amount = wine_delivery_amount;
	}

	public double getWine_transport_amount() {
		return wine_transport_amount;
	}

	public void setWine_transport_amount(double wine_transport_amount) {
		this.wine_transport_amount = wine_transport_amount;
	}

	public double getMax_winestock_amount() {
		return max_winestock_amount;
	}

	public void setMax_winestock_amount(double max_winestock_amount) {
		this.max_winestock_amount = max_winestock_amount;
	}	
	
	
}
