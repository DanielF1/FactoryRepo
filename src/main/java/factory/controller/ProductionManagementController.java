package factory.controller;
//
//public class ProductionManagementController {



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

	//bei Überschuss wird diese Rechnung ausgelöst
//	public Transport deliverWine(int quantity, Date date, Location location)
//			throws Exception {
//		WineDepartment dept = location.getWineDepartment();
//		if (dept == null)
//			throw new Exception();
//
//		if (!dept.isOverflow(quantity)) {
//			dept.deliverWine(quantity);
//			return null;
//		}
//
//		for (Location loc : getLocations()) {
//			WineDepartment locDept = location.getWineDepartment();
//			if (dept == null)
//				continue;
//
//			if (!dept.isOverflow(quantity)) {
//				locDept.deliverWine(quantity);
//				Transport transport = new Transport(location, loc, quantity);
//				return transport;
//			}
//		}
//
//		dept.deliverWine(quantity);
//		return null;
//	}
//}	
//}
