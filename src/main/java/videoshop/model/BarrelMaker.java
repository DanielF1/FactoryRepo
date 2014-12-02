package videoshop.model;
//package kickstart.model;
//
//
//
//import java.time.LocalDate;
//
//
//public class Fassbinder {
//	private Braumeister braumeister;
//
//	private BarrelStorageArea barrelStorageArea;
//	
//	public Fassbinder(BarrelStorageArea barrelStorageArea) {
//		this.barrelStorageArea = barrelStorageArea;
//	}
//
//	public void registriereBraumeister(Braumeister braumeister) {
//		this.braumeister = braumeister;
//	}
//
//	public Barrel erzeugeFass() {
//		Barrel barrel = new Barrel((long) (Barrel.barrelCount+1),LocalDate.now(), BarrelContentType.Leer, 20,LocalDate.now(), LocalDate.now().plusDays(2));
//		return barrel;
//	}
//	
//	public void erzeugeFassUndLagerEs(){
//		Barrel fass = erzeugeFass();
//		barrelStorageArea.saveBarrel(fass);
//	}
//	
//	/*
//	 * Diese Funktion wird aufgerufen, wenn ein volles Fass vorhanden ist
//	 */
//	public void benachrichtigeBraumeister(Barrel fass) {
//		// Leeres Fass erzeugen oder aus dem Lager suchen...
//		Barrel neuesFass = erzeugeFass();
//		// Wenn der Braumeister das Fass umgefüllt hat...
//		if (!braumeister.gibtAltesFass(fass, neuesFass)){
//			// Umfüllen hat nicht funktioniert
//			return;
//		} 
//		// Es hat funktioniert
//		barrelStorageArea.deleteBarrel(fass.getID());
//		barrelStorageArea.saveBarrel(neuesFass);
//	}
//}
