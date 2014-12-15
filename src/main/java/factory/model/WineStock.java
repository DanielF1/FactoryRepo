package factory.model;

public class WineStock {
	private double quantity; //Wert gibt an, wie viele Einheiten sich im Lager befinden
	private double capacity; //Wert gibt an, wie viel Produktionskapazität noch vorhanden ist
	
	public WineStock(double quantity, double capacity) {
		this.quantity = quantity;
		this.capacity = capacity;
	}
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	
	
}
