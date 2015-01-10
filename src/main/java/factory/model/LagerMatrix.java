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
//		resetLager();
		boolean lagerFL = (this.lagerType == "FLL-R")?true:false;
		if (allBarrels == null)
			return;
		if (lagerFL){
		for(Barrel bar:allBarrels){
			if(bar.getPosition().equals("") && bar.getQuality().equals("")){
				this.getNextFreePos();
				this.Lager[this.next.getRegal()][this.next.getPlatz()] = bar.getId();
				int x = this.next.getRegal()+1;
				int y = this.next.getPlatz()+1;
				bar.setPosition(this.getLagerType() + x  + "-" + y );
				System.out.println(bar.getPosition());
			}
			this.next = null;
		}
		}
		else {
			for(Barrel bar:allBarrels){

				if(bar.getPosition().equals("")&&!bar.getQuality().equals("")){
					this.getNextFreePos();
					this.Lager[this.next.getRegal()][this.next.getPlatz()] = bar.getId();
					int x = this.next.getRegal()+1;
					int y = this.next.getPlatz()+1;
					bar.setPosition(this.getLagerType() + x  + "-" + y );
					System.out.println(bar.getPosition());
				}
				this.next = null;
			}
		}
	
	}
	
	private void resetLager(){
		List<Barrel> allBarrels = barrelstock.getBarrels();
		int x=0;
		for (Barrel barrel: allBarrels)
		{
		for(Integer i = 1;i<= AnzahlRegale;i++){
			for(Integer j =1;i<= AnzahlPlatzeProRegal;j++){

					if (barrel.getId()==Lager[i][j])
					{
						x++;
					}
				}
				//TO DO , pruefe ob der Fass mit dem folgenden ID aus der Matrix immer noch existiert in der DatenBank,
				// wenn nicht dann lösche ihn aus der LagerMatrix (Lager[i][j] = null).
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
			for(Integer i =AnzahlRegale-1;i>=0;i--){
				for(Integer j =AnzahlPlatzeProRegal-1;j>=0 ;j--){
					if(Lager[i][j] == null){
						this.next = new Pair(i,j);
						i = this.next.getRegal();
						j = this.next.getPlatz();
					}
				}
			}
			
	}
}



