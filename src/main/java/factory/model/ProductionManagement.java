//package factory.model;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductionManagement {
//
//	private static final int DAY_IN_MILLIS = 24 * 3600 * 1000;
//	private static final int VOLUME_HEKTOLITERS_IN_TWO_DAYS = 24;
	
/*
 * Datum wäre gut ;)
 */
//	public long daysTillAprilTheFirst(Date date){
//		Calendar cal = Calendar.getInstance();
//		Calendar delDate = Calendar.getInstance();
//		delDate.setTime(date);
////		int curYear = cal.get(Calendar.YEAR); // Das aktuelle Jahr, oder das Jahr von date?
//		int curYear = delDate.get(Calendar.YEAR); // Das aktuelle Jahr, oder das Jahr von date?
//		int curMonth = cal.get(Calendar.MONTH);
//		cal.set(curYear + (curMonth >= Calendar.APRIL ? 1:0), Calendar.APRIL, 1, 0, 0, 0);
//		long thenMillis = cal.getTimeInMillis();
//		long daysBetweenDates = (thenMillis - delDate.getTimeInMillis()) ;
//		return TimeUnit.DAYS.convert(daysBetweenDates, TimeUnit.MILLISECONDS);
////		return daysBetweenDates;	
//	}
//
//	public int countCapacityInHektoLiter (Date date){
//		long daysTillAprilTheFirst = daysTillAprilTheFirst(date);
//		long s = daysTillAprilTheFirst / 2 * VOLUME_HEKTOLITERS_IN_TWO_DAYS;
//		return (int)s;
//		
//	}
//    private	int wineQuantity;
//    
//	public boolean isOverflow (int hektoliters, Date date){
//		int countCapacityInHektoLiter = countCapacityInHektoLiter(date);
//		return wineQuantity+hektoliters > countCapacityInHektoLiter ? true : false;
//	}
//		
//	public int overflowQuantity (int quantity, Date date){
//		return quantity - (countCapacityInHektoLiter(date) - wineQuantity);
//	}
//	
//	public void deliverWine (int quantity){
//		wineQuantity+= quantity;
//	}
//	
//
//	public int getWineQuantity() {
//		return wineQuantity;
//	}
//

//}
