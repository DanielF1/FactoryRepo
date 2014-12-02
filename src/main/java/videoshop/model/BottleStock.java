package videoshop.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BottleStock {

	private @Id @GeneratedValue Long id;
	private String name;
	private int quantity_empty;
	private int quantity_full;

	@OneToMany(cascade = CascadeType.ALL)
	private  List<Bottle> emptybottles = new ArrayList<Bottle>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private  List<Bottle> fullbottles = new ArrayList<Bottle>();
	
	public BottleStock(String name, int quantity_empty, List<Bottle> emptybottles, int quantity_full, List<Bottle> fullbottles)
	{
		this.name = name;
		this.quantity_empty = quantity_empty;
		this.emptybottles = emptybottles;
		this.quantity_full = quantity_full;
		this.fullbottles = fullbottles;
	}



	BottleStock(){}
	
//	public int getAmount() {
//		return amount;
//	}
//
//	public void setAmount(int amount) {
//		this.amount = amount;
//	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return quantity_empty;
	}

	public void setQuantity_empty(int quantity_empty) {
		this.quantity_empty = quantity_empty;
	}

	public int getQuantity_full() {
		return quantity_full;
	}

	public void setQuantity_full(int quantity_full) {
		this.quantity_full = quantity_full;
	}
}
