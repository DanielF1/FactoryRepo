package factory.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MasterBrewer {

	private BarrelStock barrelstock;

	public MasterBrewer(BarrelStock barrelstock) {
		this.barrelstock  = barrelstock;
	}

	public boolean gibtAltesFass(Barrel altesFass, Barrel neuesFass) {
		neuesFass.setContent(altesFass.getContent());
		neuesFass.setAmount(altesFass.getAmount());
		altesFass.setContent("");
		altesFass.setAmount(0);
		return true;
	}

	public void zusammenschuetten() {
		Iterable<Barrel> allBarrels = barrelstock.getAllBarrels();
		HashMap<String, List<Barrel>> map = new HashMap<String, List<Barrel>>();
		// Nur barrels mit Inhalt einer Art und GLEICHEN ALTER finden

		for (Barrel barrel : allBarrels) {
			String inhalt = barrel.getContent();
			if (!map.containsKey(inhalt)) {
				map.put(inhalt, new ArrayList<Barrel>());
			}
			map.get(inhalt).add(barrel);
		}

		for (String key : map.keySet()) {
			List<Barrel> list = map.get(key);

			// String zu Date, oder Alter oder ... ändern
			HashMap<String, List<Barrel>> inhaltMap = new HashMap<String, List<Barrel>>();
			for (Barrel barrel : list) {
				// bearbeiten Alter finden 
				String hausaufgabe = barrel.getContent().toString();
				if (!inhaltMap.containsKey(hausaufgabe)) {
					inhaltMap.put(hausaufgabe, new ArrayList<Barrel>());
				}
				inhaltMap.get(hausaufgabe).add(barrel);
			}

			for (String key1 : inhaltMap.keySet()) {
				schuette(inhaltMap.get(key1));
			}

		}

	}

	private void schuette(List<Barrel> list) {
		double hilfsFass = 0;
		for (Barrel barrel : list) {
			hilfsFass += barrel.getAmount();
			barrel.setAmount(0);
		}
		for (Barrel barrel : list) {
			// Konstante definieren
			double amount = 30;
			if (hilfsFass < amount)
				amount = hilfsFass;
			barrel.setAmount(amount);
			hilfsFass -= amount;
			barrelstock.saveBarrel(barrel);
		}
	}
}
