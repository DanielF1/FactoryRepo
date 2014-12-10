package factory.model;


public class BarrelStock {
	BarrelList barrellist;
	
	public BarrelStock(BarrelList barrellist) {
		this.barrellist = barrellist;
	}

	public Iterable<Barrel> getAllBarrels() {
		return barrellist.findAll();
	}
    
	public Barrel findOneBarrel(Long Id){
		return barrellist.findOne(Id);
	}
	
	public void deleteOne(Long Id){
		barrellist.delete(Id);
	}
	
	public void saveBarrel(Barrel barrel) {
		barrellist.save(barrel);
	}

}
