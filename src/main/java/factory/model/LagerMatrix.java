package factory.model;

import java.util.List;




public class LagerMatrix {
	private String lagerType;
	
	public String getLagerType() {
		return lagerType;
	}

	public void setLagerType(String lagerType) {
		this.lagerType = lagerType;
	}
	private Long[][] Lager;
	
	
	public Long[][] getLager() {
		return Lager;
	}

	public void setLager(Long[][] lager) {
		Lager = lager;
	}
	static final int AnzahlPlatzeProRegal = 5;
	static final int AnzahlRegale = 100;
	private BarrelStock barrelstock;
	private final DepartmentRepository departmentRepository;
	private Pair next;
	
	public LagerMatrix(DepartmentRepository departmentRepository, BarrelStock barrelstock, String lagetType){	
		this.departmentRepository = departmentRepository;
		this.barrelstock = barrelstock;
		this.lagerType = lagetType;
		Lager = new Long[AnzahlRegale][AnzahlPlatzeProRegal];
	}
	
	public void zuordnen(List<Barrel> allBarrels){
//		List<Barrel> allBarrels = barrelstock.getBarrels();
		// ist der Lager fuer leere Faesser vorgesehen, oder nicht.
		boolean lagerFL = (this.lagerType == "FLL-R")?true:false;
		if (allBarrels == null)
			return;
		if (lagerFL){
		for(Barrel bar:allBarrels){
			if(bar.getPosition().equals("") && bar.getQuality().equals("")){
				this.getNextFreePos();
				this.Lager[this.next.getRegal()][this.next.getPlatz()] = bar.getId();
				bar.setPosition(this.getLagerType() + this.next.getRegal()  + "-" +  this.next.getPlatz());
				System.out.println(bar.getPosition());
			}
//			departmentRepository.save(barrelstock);
			this.next = null;
		}
		}
		else {
			for(Barrel bar:allBarrels){
				boolean barLeer = !bar.getQuality().equals("");
				if(bar.getPosition().equals("")&&barLeer){
					this.getNextFreePos();
					this.Lager[this.next.getRegal()][this.next.getPlatz()] = bar.getId();
					bar.setPosition(this.getLagerType() + this.next.getRegal()  + "-" +  this.next.getPlatz());
					System.out.println(bar.getPosition());
				}
//				departmentRepository.save(barrelstock);
				this.next = null;
			}
		}
	
	}
	
	public void getNextFreePos(){
//		Integer i;
//		Integer j;
//		if(this.next == null){
//			i = 0;
//			j = 0;
//		}else{
//			i = this.next.getRegal();
//			j = this.next.getPlatz();
//		}
			for(Integer i =0;i< AnzahlRegale;i++){
				for(Integer j =0;j< AnzahlPlatzeProRegal;j++){
					if(Lager[i][j] == null){
						this.next = new Pair(i,j);
						i = this.next.getRegal();
						j = this.next.getPlatz();
					}
				}
			}
			
	}
}



