package factory.model;


import java.time.LocalDate;

public class BarrelStock {
	BarrelList barrelstock;
	private BarrelMaker barrelmaker;
	private MasterBrewer barrelbrewer;

	public BarrelStock(BarrelList barrelstock) {
		this.barrelstock = barrelstock;
		barrelmaker = new BarrelMaker(this);
		barrelbrewer = new MasterBrewer(this);
		barrelmaker.registriereBraumeister(barrelbrewer);
	}

	public Iterable<Barrel> getAllBarrels() {
		return barrelstock.findAll();
	}

	public void saveBarrel(Barrel barrel) {
		barrelstock.save(barrel);
	}

	public void deleteBarrel(Long iD) {
		Barrel barrel = barrelstock.findOne(iD);
		// Fass ist leer
		if (barrel.getDeath_of_barrel().compareTo(LocalDate.now())<=0)
		{
			// Fass Ablaufdateum ist in der Vergangenheit
			if (barrel.getContent().equals("")) {
				barrelstock.delete(iD);
				return;
			}
			// Ansonsten... 
			barrelmaker.benachrichtigeBraumeister(barrel);
		}
	}

	public BarrelMaker getBarrelmaker() {
		return barrelmaker;
	}

	public void setBarrelmaker(BarrelMaker barrelmaker) {
		this.barrelmaker = barrelmaker;
	}	

	public MasterBrewer getMasterBrewer() {
		return barrelbrewer;
	}

	
}
