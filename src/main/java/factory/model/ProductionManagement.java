package factory.model;

import java.util.Calendar;

public class ProductionManagement {
	
	private static final int DAY_IN_MILLIS = 24 * 3600 * 1000;
	private static final int VOLUME_HEKTOLITERS_IN_TWO_DAYS = 24;
	
	
	
	public int daysTillAprilTheFirst(){
		Calendar cal = Calendar.getInstance();
		long nowMillis = cal.getTimeInMillis();
		int curYear = cal.get(Calendar.YEAR);
		int curMonth = cal.get(Calendar.MONTH);
		cal.set(curYear + (curMonth >= Calendar.APRIL ? 1:0), Calendar.APRIL, 1, 0, 0, 0);
		long thenMillis = cal.getTimeInMillis();
		int daysBetweenDates = (int) (thenMillis - nowMillis) / DAY_IN_MILLIS;
		
		return daysBetweenDates;	
	}
	
	
	public int countCapacityInHektoLiter (){
		return daysTillAprilTheFirst() / 2 * VOLUME_HEKTOLITERS_IN_TWO_DAYS;
		
	}
    private	int wineQuantity;
    
	public boolean isOverflow (int hektoliters){
		return wineQuantity > countCapacityInHektoLiter() ? true : false;
	}
		
	public int overflowQuantity (int quantity){
		return wineQuantity - countCapacityInHektoLiter();
	}
	
	public void deliverWine (int quantity){
		wineQuantity+= quantity;
	}
	

	public int getWineQuantity() {
		return wineQuantity;
	}

	
	
	
}
