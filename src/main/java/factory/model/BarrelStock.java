package factory.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BarrelStock{
	
	private @Id @GeneratedValue Long id;
	private BarrelMaker barrelmaker;
	private MasterBrewer masterbrewer;
	private Barrel barrel;
	
	@OneToMany(cascade = CascadeType.ALL)
	private  List<Barrel> barrels = new ArrayList<Barrel>();
	
	public BarrelStock(List<Barrel> barrel) {
		barrels = barrel;
		barrelmaker = new BarrelMaker(this);
		masterbrewer = new MasterBrewer();
		barrelmaker.registriereBraumeister(masterbrewer);
	}


	public BarrelStock(){}

	public void deleteBarrel(Long id) {
		
		if(barrel.getId().equals(id)){
			// Fass ist leer
			if (barrel.getDeath_of_barrel().compareTo(LocalDate.now())<=0)
			{
					// Fass Ablaufdateum ist in der Vergangenheit
				if (barrel.getContent() == null) {
					barrels.remove(id);
					return;
				} 
				else 
				{
					barrelmaker.benachrichtigeBraumeister(barrel);
				} // /else
			} // /if
		} // /if
	}
	
	public void saveBarrel(Barrel barrel){
		barrels.add(barrel);
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Barrel> getBarrels() {
		return barrels;
	}

	public void setBarrels(List<Barrel> barrels) {
		this.barrels = barrels;
	}


	public BarrelMaker getBarrelmaker() {
		return barrelmaker;
	}

	public Barrel getBarrel() {
		return barrel;
	}


	public void setBarrel(Barrel barrel) {
		this.barrel = barrel;
	}


	public void setBarrelmaker(BarrelMaker barrelmaker) {
		this.barrelmaker = barrelmaker;
	}


}
