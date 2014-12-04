package factory.model;

public class Transport {

	public Transport(Location src, Location dst, int quantity) {
		id = 27;
		source = src;
		destination = dst;
		menge = quantity;
	}

	int id;
	int menge;
	Location source;
	Location destination;

}
