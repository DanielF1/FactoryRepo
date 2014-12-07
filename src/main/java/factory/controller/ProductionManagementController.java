package factory.controller;
//
//public class ProductionManagementController {

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
