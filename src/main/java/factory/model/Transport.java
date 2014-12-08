package factory.model;

public class Transport {

	public Transport(Location src, Location dst, int quantity) {
		id = 27;
		source = src;
		destination = dst;
		this.setQuantity(quantity);
	}

	int id;
	private int quantity;
	private Location source;
	private Location destination;

	public Location getDestination() {
		return destination;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
