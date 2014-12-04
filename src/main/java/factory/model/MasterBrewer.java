package factory.model;

public class MasterBrewer {

	public boolean gibtAltesFass(Barrel altesFass, Barrel neuesFass) {
		neuesFass.setContent(altesFass.getContent());
		altesFass.setContent(null);
		return true; 
	}
  
}
