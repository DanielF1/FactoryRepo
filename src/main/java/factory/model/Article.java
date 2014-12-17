package factory.model;

import javax.persistence.Entity;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;


@Entity
public class Article extends Product{
	
		private String image;
		private String alter;
		private String alkoholgehalt;
		private String volumen;
		private String cognacart;

		public Article(){}
		
		public Article( String image, String name, String alter, Money preis, String alkoholgehalt, String volumen, String cognacart) {
			
			super(name, preis, Units.METRIC);
			
			this.image = image;
			this.alter = alter;
			this.alkoholgehalt = alkoholgehalt;
			this.volumen = volumen;
			this.cognacart = cognacart;
		}
		
		public String getImage(){
			return this.image;
		}
		
		public void setImage(String Image){
			
			this.image=Image;
		}
	
		public String getAlter(){
			return this.alter;
		}
		
	/*	
		public String setAlter(){
			return alter;
		}
		*/
		
		public void setAlter(String Alter){
			
			this.alter=Alter;
		}

		public String getAlkoholgehalt(){
			return this.alkoholgehalt;
		}
		
		public void setAlkoholgehalt(String Alkoholgehalt){
			
			this.alkoholgehalt=Alkoholgehalt;
		}
	
		public String getVolumen(){
			return this.volumen;
		}
		
		public void setVolumen(String Volumen){
			
			this.volumen=Volumen;
		}
	
		public String getCognacart(){
			return this.cognacart;
		}
		
		public void setCognacart(String Cognacart){
			
			this.cognacart=Cognacart;
		}

		public void delete(Long id) {
			
		}	

}
