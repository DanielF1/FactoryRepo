package factory.model;

public class MaxBottleStore {
	String name;
	int quantity;
	double amount;
	
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param quantity
	 * @param amount
	 */
	public MaxBottleStore(String name,int quantity,double amount){
		this.name = name;
		this.quantity=quantity;
		this.amount = amount;
	}
	
	/**
	 * Getter and Setter
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}	
}
