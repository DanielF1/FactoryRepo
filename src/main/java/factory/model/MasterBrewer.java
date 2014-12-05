package factory.model;

public class MasterBrewer {

	public boolean gibtAltesFass(Barrel altesFass, Barrel neuesFass) {
		neuesFass.setContent(altesFass.getContent());
		neuesFass.setAmount(altesFass.getAmount());
		altesFass.setContent("");
		altesFass.setAmount(0);
		return true; 
	}
  
}
