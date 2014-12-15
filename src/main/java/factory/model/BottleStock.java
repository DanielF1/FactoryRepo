package factory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class BottleStock extends Department{
	
	private int quantity_empty;
	private int quantity_full;
	@OneToMany(cascade = CascadeType.ALL)
	private  List<Bottle> emptybottles;	
	@OneToMany(cascade = CascadeType.ALL)
	private  List<Bottle> fullbottles;
	
	@Deprecated
	public BottleStock(){}
	
	public BottleStock(String name, List<Bottle> emptybottles, List<Bottle> fullbottles){
		
		super(name);
		this.emptybottles = emptybottles;
		this.fullbottles = fullbottles;
	}

	public List<Bottle> getEmptybottles() {
		return emptybottles;
	}
	
	public void setEmptybottles(List<Bottle> emptybottles) {
		this.emptybottles = emptybottles;
	}

	public List<Bottle> getFullbottles() {
		return fullbottles;
	}

	public void setFullbottles(List<Bottle> fullbottles) {
		this.fullbottles = fullbottles;
	}
	
	public int getQuantity_empty() {
		return emptybottles.size();
	}

	public void setQuantity_empty(int quantity_empty) {
		this.quantity_empty = quantity_empty;
	}

	public int getQuantity_full() {
		return fullbottles.size();
	}

	public void setQuantity_full(int quantity_full) {
		this.quantity_full = quantity_full;
	}
}
