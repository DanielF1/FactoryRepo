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
	
	/**
	 * Default Constructor
	 */
	public ProductionMonth(){}
	
	/**
	 * Constructor
	 * 
	 * @param name Name des Produktionsmonats
	 * @param length Länge des Produktionsmonats
	 * @param processing_through_stills produzierte Destillen des Produktionsmonats
	 * @param wine_delivery_amount Weinlieferungsmenge des Produktionsmonats
	 * @param wine_transport_amount Weintransportmenge des Produktionsmonats 
	 * @param max_winestock_amount maximale Weinlagermenge des Produktionsmonats
	 */
	
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
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * setter
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * getter
	 * @return processing_through_stills
	 */
	public double getProcessing_through_stills() {
		return processing_through_stills;
	}

	/**
	 * setter
	 * @param processing_through_stills
	 */
	public void setProcessing_through_stills(double processing_through_stills) {
		this.processing_through_stills = processing_through_stills;
	}

	/**
	 * getter
	 * @return wine_delivery_amount
	 */
	public double getWine_delivery_amount() {
		return wine_delivery_amount;
	}

	/**
	 * setter
	 * @param wine_delivery_amount
	 */
	public void setWine_delivery_amount(double wine_delivery_amount) {
		this.wine_delivery_amount = wine_delivery_amount;
	}

	/**
	 * getter
	 * @return wine_transport_amount
	 */
	public double getWine_transport_amount() {
		return wine_transport_amount;
	}

	/**
	 * setter
	 * @param wine_transport_amount
	 */
	public void setWine_transport_amount(double wine_transport_amount) {
		this.wine_transport_amount = wine_transport_amount;
	}

	/**
	 * getter
	 * @return  max_winestock_amount
	 */
	public double getMax_winestock_amount() {
		return max_winestock_amount;
	}

	/**
	 * setter
	 * @param max_winestock_amount
	 */
	public void setMax_winestock_amount(double max_winestock_amount) {
		this.max_winestock_amount = max_winestock_amount;
	}	
}
