package factory.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationManagement {

	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentRepository;
	private final TransportRepository transportRepository;
	private static final int DAY_IN_MILLIS = 24 * 3600 * 1000;
	private static final int VOLUME_HEKTOLITERS_IN_TWO_DAYS = 24;

	@Autowired
	public LocationManagement(	LocationRepository locationRepository,
								DepartmentRepository departmentRepository,
								TransportRepository transportRepository) {
		this.locationRepository = locationRepository;
		this.departmentRepository = departmentRepository;
		this.transportRepository = transportRepository;

	}

	// check if this Location contains Production Department
	public boolean containsProductionmanagement(Location location) {

		for (Department dept : location.getDepartments()) {
			if (dept.getName().contains("Produktion")) {
				return true;
			}
		}
		return false;
	}
	
	public Department getProductionDepartment(Long id) {
		Location location = locationRepository.findOne(id);
		List<Department> deps = location.getDepartments();
		for (Department dep : deps) {
			if (dep.getName().contains("Produktion")) {
				return dep;
			}
		}
		return null;
	}


	// Location List with production
	public List<Location> getLocationsListWithProductionManagement() {
		List<Location> result = new ArrayList<Location>();
		for (Location location : locationRepository.findAll()) {
			if (containsProductionmanagement(location)) {
				result.add(location);
			}
		}
		return result;
	}

	public Transport deliverWine(double deliveredAmount, Date date, Long lid) {
			
		Department dept = getProductionDepartment(lid);
		Long id = dept.getId();
		double oldQuantity = dept.getQuantity();

		// no overflow in department
		if (!isOverflow(oldQuantity, deliveredAmount, date)) {
			deliverWine(oldQuantity, deliveredAmount, dept);
			return null;
		} else {
			// count overflow
			double overflow = overflowQuantity(oldQuantity, deliveredAmount, date);
			
			// deliver wine (under overflow)
			deliverWine(oldQuantity, deliveredAmount - overflow, dept);

			Transport ret = distributeOverflowWine(overflow, date, id);
			
			if (ret == null) {
				
				deliverWine(oldQuantity, deliveredAmount, dept);
			}
			// else {
			// // Transpoirt wurde erschaffen
			//
			// }
			return ret;
		}

	}	

	public Transport distributeOverflowWine(double quantity, Date date,
			Long firstLocation) {
		Long id = findMaxCapacity();
		// no more free capacity
		if (id == -1) {
			// deliverWine(quantity, date, firstLocation);
			return null;
		}
		deliverWine(quantity, date, id);
		Transport entity = new Transport("Wein", quantity, firstLocation, id);
		transportRepository.save(entity);
		return entity;
	}

	public Long findMaxCapacity() {
		// -1 ==> no more free capacity
		Long id = -1L;
		double freeCapacity = 0;
		for (Location location : getLocationsListWithProductionManagement()) {
			for (Department department : location.getDepartments()) {
				if (department.getName().contains("Produktion")) {
					// # TODO count capacity
					if (department.getCapacity() - department.getQuantity() > freeCapacity) {
						freeCapacity = department.getCapacity()
								- department.getQuantity();
						id = location.getId();
					}
				}
			}
		}
		return id;
	}

	// FUNKTIONEN AUS PRODUCTIONMANAGEMENT
	public long daysTillAprilTheFirst(Date date) {
		Calendar cal = Calendar.getInstance();
		Calendar delDate = Calendar.getInstance();
		delDate.setTime(date);
		// int curYear = cal.get(Calendar.YEAR); // Das aktuelle Jahr, oder das
		// Jahr von date?
		int curYear = delDate.get(Calendar.YEAR); // Das aktuelle Jahr, oder das
													// Jahr von date?
		int curMonth = cal.get(Calendar.MONTH);
		cal.set(curYear + (curMonth >= Calendar.APRIL ? 1 : 0), Calendar.APRIL,
				1, 0, 0, 0);
		long thenMillis = cal.getTimeInMillis();
		long daysBetweenDates = (thenMillis - delDate.getTimeInMillis());
		return TimeUnit.DAYS.convert(daysBetweenDates, TimeUnit.MILLISECONDS);
		// return daysBetweenDates;
	}

	public double countCapacityInHektoLiter(Date date) {
		long daysTillAprilTheFirst = daysTillAprilTheFirst(date);
		long s = daysTillAprilTheFirst / 2 * VOLUME_HEKTOLITERS_IN_TWO_DAYS;
		return (int) s;

	}

	public boolean isOverflow(double oldQuantity, double newQuantity, Date date) {

		double countCapacityInHektoLiter = countCapacityInHektoLiter(date);
		return oldQuantity + newQuantity > countCapacityInHektoLiter ? true
				: false;
	}


	public double overflowQuantity(double oldQuantity, double deliveredAmount,
			Date date) {
		return (oldQuantity + deliveredAmount - (countCapacityInHektoLiter(date)));
	}

	public void deliverWine(double oldQuantity, double newQuantity,
			Department dept) {
		double quantity = oldQuantity + newQuantity;
		dept.setQuantity(quantity);
		departmentRepository.save(dept);
		// #TODO count capacity
		double newCapacity = dept.getCapacity() - quantity;
		dept.setCapacity(newCapacity);
		departmentRepository.save(dept);
	}

}
