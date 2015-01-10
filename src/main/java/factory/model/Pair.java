package factory.model;

public class Pair {
	private Integer platz;
	private Integer regal;
	
	public Integer getPlatz() {
		return platz;
	}

	public void setPlatz(Integer platz) {
		this.platz = platz;
	}

	public Integer getRegal() {
		return regal;
	}

	public void setRegal(Integer regal) {
		this.regal = regal;
	}

	public Pair(Integer regal, Integer platz){
		this.platz = platz;
		this.regal = regal;
	}

	public boolean istGrosserAls(Pair pair){
		return (pair.getRegal() <= this.getRegal() && pair.getPlatz() < this.getPlatz()) ? true : false;
	}
}

