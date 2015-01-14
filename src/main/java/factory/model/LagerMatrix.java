package factory.model;

import java.util.List;

import factory.repository.DepartmentRepository;


public class LagerMatrix {
	private String lagerType;
	private Long[][] Lager;
	static final int AnzahlPlatzeProRegal = 5;
	static final int AnzahlRegale = 100;
	private BarrelStock barrelstock;
	private final DepartmentRepository departmentRepository;
	private Pair next;
	
	/**
	 * Constructor
	 * 
	 * @param departmentRepository Abteilungsrepository
	 * @param barrelstock Fasslager
	 * @param lagetType Lagertyp
	 */
	public LagerMatrix(DepartmentRepository departmentRepository, BarrelStock barrelstock, String lagetType){	
		this.departmentRepository = departmentRepository;
		this.barrelstock = barrelstock;
		this.lagerType = lagetType;
		Lager = new Long[AnzahlRegale][AnzahlPlatzeProRegal];
	}
	
	
	/**
	 * getter 
	 * @return lagerType
	 */
	public String getLagerType() {
		return lagerType;
	}

	/**
	 * setter
	 * @param lagerType
	 */
	public void setLagerType(String lagerType) {
		this.lagerType = lagerType;
	}

	/**
	 * getter
	 * @return Lager
	 */
	public Long[][] getLager() {
		return Lager;
	}

	/**
	 * setter
	 * @param lager
	 */
	public void setLager(Long[][] lager) {
		Lager = lager;
	}

	/**
	 * @param allBarrels alle Fässer auf bestimmte Positionen zurordnen 
	 */
	public void zuordnen(List<Barrel> allBarrels){
		resetLager(allBarrels);
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
	
	/**
	 * Prüf ob das Fass mit der folgenden ID aus der Matrix immer noch in der DatenBankexistiert,
	 * wenn nicht,wird das aus der LagerMatrix (Lager[i][j] = null) gelöscht.
	 * 
	 * @param allBarrels alle Fässer auf bestimmte Positionen zurordnen 
	 */
	public void resetLager(List<Barrel> allBarrels){

		int x=0;	
		for(Integer i = 0;i< AnzahlRegale;i++){
			for(Integer j =0;j< AnzahlPlatzeProRegal;j++){
				for (Barrel barrel: allBarrels)
				{
					if (barrel.getId()==Lager[i][j])
					{
						x++;
					}
				}
				if (x==0) Lager[i][j]=null;
				if (x!=0) x=0;
				
			}
		
		}	
		
	}
	
	/**
	 * Berechnen die Position im Lager für das Fass
	 */
	public void getNextFreePos(){

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