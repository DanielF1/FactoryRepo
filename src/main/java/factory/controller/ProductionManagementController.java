package factory.controller;


/*

public class ProductionManagementController {

	// if overflow
	
	public Transport deliverWine(int quantity, Date date, Location location){
	//		throws Exception {
	//	ProductionManagement dept = location.getWineDepartment();
	//	if (dept == null)
	//		throw new Exception();
		// if Oberflow
		ProductionManagement dept = location.getProductionManagementDepartment();
		if (!dept.isOverflow(quantity)) {
			dept.deliverWine(quantity);
			return null;
		}

		for (Location loc : Location.getLocationsListWithProductionManagement()) {
			ProductionManagement locDept = loc.getProductionManagementDepartment();
			if (!locDept.isOverflow(quantity)) {
				
				locDept.deliverWine(quantity);
				Transport transport = new Transport(location, loc, quantity);
				return transport;
			}
		}
		
		dept.deliverWine(quantity);
		return null;
	}
}	

*/