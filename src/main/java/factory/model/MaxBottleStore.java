package factory.model;

public class MaxBottleStore {
	String name;
	int quantity;
	double amount;
	

	/**
	 * Constructor
	 * 
	 * @param name Name des Maximalen Flaschenagers
	 * @param quantity Quantität des Maximalen Flaschenagers
	 * @param amount Menge des Maximalen Flaschenagers
	 */
	
	
	public MaxBottleStore(String name,int quantity,double amount){
		this.name = name;
		this.quantity=quantity;
		this.amount = amount;
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
	 * @return quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * setter
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * getter
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/** 
	 * setter
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}	
}
