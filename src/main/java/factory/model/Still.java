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

	/**
	 * Constructor
	 * 
	 * @param amount Menge der Destille
	 * @param status_one Status der Destille
	 * @param status_two Status der Destille
	 * @param still_process_start_time Destillenprozess startzeit der Destille
	 * @param still_process_end_time Destillenprozess endzeit der Destille
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
	
	
	/**
	 * default Constructor
	 */
	@Deprecated
	public Still(){}
	

	/**
	 * getter
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * setter
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * getter
	 * @return  status_one
	 */
	public int getStatus_one() {
		return status_one;
	}

	/**
	 * setter
	 * @param status_one
	 */
	public void setStatus_one(int status_one) {
		this.status_one = status_one;
	}

	/**
	 * getter
	 * @return  status_two
	 */
	public int getStatus_two() {
		return status_two;
	}

	/**
	 * setter
	 * @param status_two
	 */
	public void setStatus_two(int status_two) {
		this.status_two = status_two;
	}

	/**
	 * getter
	 * @return still_process_start_time
	 */
	public LocalDateTime getStill_process_start_time() {
		return still_process_start_time;
	}

	/**
	 * setter
	 * @param still_process_start_time
	 */
	public void setStill_process_start_time(LocalDateTime still_process_start_time) {
		this.still_process_start_time = still_process_start_time;
	}

	/**
	 * getter
	 * @return still_process_end_time
	 */
	public LocalDateTime getStill_process_end_time() {
		return still_process_end_time;
	}

	/**
	 * setter
	 * @param still_process_end_time
	 */
	public void setStill_process_end_time(LocalDateTime still_process_end_time) {
		this.still_process_end_time = still_process_end_time;
	}
}
