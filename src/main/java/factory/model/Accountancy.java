package factory.model;

import javax.persistence.Entity;

@Entity
public class Accountancy extends Department {
	
	private double income;
	private double expenditure;
	
	@Deprecated
	public Accountancy() {}

	public Accountancy(String name, double income, double expenditure){
		
		super(name);
		this.income = income;
		this.expenditure = expenditure;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(double expenditure) {
		this.expenditure = expenditure;
	}
}
