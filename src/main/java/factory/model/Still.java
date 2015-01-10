package factory.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Still {

	private @Id @GeneratedValue Long id;
	private int amount;
	
	/*
	 * ======================================
	 * 			0 == free
	 * 			1 == still is running
	 * 			2 == still is ready
	 * ======================================
	 */
	private int status_one;
	private int status_two;
	
	private LocalDateTime still_process_start_time = null;
	private LocalDateTime still_process_end_time = null;
	
	
	/*
	 * Konstruktor
	 */
	public Still(int amount, int status_one, int status_two,
			LocalDateTime still_process_start_time,	LocalDateTime still_process_end_time) 
	{
		this.amount = amount;
		this.status_one = status_one;
		this.status_two = status_two;
		this.still_process_start_time = still_process_start_time;
		this.still_process_end_time = still_process_end_time;
	}
	
	/*
	 * Konstruktor
	 */
	@Deprecated
	public Still(){}
	
	
	
	/*
	 * Getter und Setter
	 */
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStatus_one() {
		return status_one;
	}

	public void setStatus_one(int status_one) {
		this.status_one = status_one;
	}

	public int getStatus_two() {
		return status_two;
	}

	public void setStatus_two(int status_two) {
		this.status_two = status_two;
	}

	public LocalDateTime getStill_process_start_time() {
		return still_process_start_time;
	}

	public void setStill_process_start_time(LocalDateTime still_process_start_time) {
		this.still_process_start_time = still_process_start_time;
	}

	public LocalDateTime getStill_process_end_time() {
		return still_process_end_time;
	}

	public void setStill_process_end_time(LocalDateTime still_process_end_time) {
		this.still_process_end_time = still_process_end_time;
	}
}
