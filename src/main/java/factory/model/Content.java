package factory.model;

import java.time.LocalDate;

public class Content {

	private double amount;
	private LocalDate producingDate;
	private String quality;
	
	public Content(double amount, LocalDate producingDate, String quality) {
		this.amount = amount;
		this.producingDate = producingDate;
		this.quality = quality;
	}
	
	public Content(){}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getProducingDate() {
		return producingDate;
	}

	public void setProducingDate(LocalDate producingDate) {
		this.producingDate = producingDate;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	};
	
	
}
