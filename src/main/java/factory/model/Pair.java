package factory.model;

public class Pair {
	private Integer platz;
	private Integer regal;
	
	/**
	 * Constructor
	 * 
	 * @param regal Regal des Paars
	 * @param platz Platz des Paars
	 */
	public Pair(Integer regal, Integer platz){
		this.platz = platz;
		this.regal = regal;
	}


	/**
	 * getter
	 * @return platz
	 */
	public Integer getPlatz() {
		return platz;
	}

	/**
	 * setter
	 * @param platz
	 */
	public void setPlatz(Integer platz) {
		this.platz = platz;
	}

	/**
	 * getter
	 * @return regal
	 */
	public Integer getRegal() {
		return regal;
	}

	/** 
	 * setter
	 * @param regal
	 */
	public void setRegal(Integer regal) {
		this.regal = regal;
	}

//	public boolean istGrosserAls(Pair pair){
//		return (pair.getRegal() <= this.getRegal() && pair.getPlatz() < this.getPlatz()) ? true : false;
//	}
}

