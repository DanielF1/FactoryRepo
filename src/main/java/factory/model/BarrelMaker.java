package factory.model;

import java.time.LocalDate;


public class BarrelMaker {
	private MasterBrewer masterbrewer;

	private BarrelStorageArea barrelstoragearea;
	
	public BarrelMaker(BarrelStorageArea barrelstoragearea) {
		this.barrelstoragearea = barrelstoragearea;
	}

	public void registriereBraumeister(MasterBrewer masterbrewer) {
		this.masterbrewer = masterbrewer;
	}

	public Barrel erzeugeFass() {
		Barrel barrel = new Barrel("", 0,LocalDate.now(),LocalDate.now(), LocalDate.now().plusDays(2));
		return barrel;
	}
	

	
	/*
	 * Diese Funktion wird aufgerufen, wenn ein volles Fass vorhanden ist
	 */
	public void benachrichtigeBraumeister(Barrel fass) {
		// Leeres Fass erzeugen 
		Barrel neuesFass = erzeugeFass();
		// Wenn der Braumeister das Fass umgefüllt hat...
		if (!masterbrewer.gibtAltesFass(fass, neuesFass)){
			// Umfüllen hat nicht funktioniert
			return;
		} 
		// Es hat funktioniert
		barrelstoragearea.deleteBarrel(fass.getId());
		barrelstoragearea.saveBarrel(neuesFass);
	}
}
