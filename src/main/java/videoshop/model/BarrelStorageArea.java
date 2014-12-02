package videoshop.model;
//package kickstart.model;
//
//import java.time.LocalDate;
//
//public class BarrelStorageArea {
//	BarrelList barrellist;
//	private Fassbinder fassbinder;
//	private Braumeister braumeister;
//
//	public BarrelStorageArea(BarrelList barrellist2) {
//		barrellist = barrellist2;
//		fassbinder = new Fassbinder(this);
//		braumeister = new Braumeister();
//		fassbinder.registriereBraumeister(braumeister);
//	}
//
//	public Iterable<Barrel> getAllBarrels() {
//		return barrellist.findAll();
//	}
//
//	public void saveBarrel(Barrel barrel) {
//		barrellist.save(barrel);
//	}
//
//	public void deleteBarrel(Long iD) {
//		Barrel barrel = barrellist.findOne(iD);
//		// Fass ist leer
//		if (barrel.getAblaufDate().compareTo(LocalDate.now())<=0)
//		{
//			// Fass Ablaufdateum ist in der Vergangenheit
//		if (barrel.getContent() == BarrelContentType.Leer) {
//			barrellist.delete(iD);
//			return;
//		}
//		// Ansonsten... 
//		fassbinder.benachrichtigeBraumeister(barrel);
//		}
//	}
//
//	public Fassbinder getFassbinder() {
//		return fassbinder;
//	}
//
//	public void setFassbinder(Fassbinder fassbinder) {
//		this.fassbinder = fassbinder;
//	}
//}
