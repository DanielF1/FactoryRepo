package factory.model;

import javax.persistence.Entity;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;

/**
 * Article ist das Produkt, welches in der Firma hergestellt und verkauft wird
 */
@SuppressWarnings("serial")
@Entity
public class Article extends Product{
	
		private String image;
		private String alter;
		private String alkoholgehalt;
		private double volumen;
		private String cognacart;
		
		/** 
		 * Default Constructor
		 */
		@Deprecated
		public Article(){}
		
		/**
		 * Constructor
		 * 
		 * @param image Es existiert ein Bild für jeden einzelnen Artikel
		 * @param name Name des Artikels
		 * @param alter Alter des Artikels
		 * @param preis Preis des Artikels
		 * @param alkoholgehalt Alkoholgehalt des Artikels
		 * @param volumen Volumen des Artikels
		 * @param cognacart Cogancart des Artikels
		 */
		public Article( String image, String name, String alter, Money preis, String alkoholgehalt, double volumen, String cognacart) {
			super(name, preis, Units.METRIC);
			
			this.image = image;
			this.alter = alter;
			this.alkoholgehalt = alkoholgehalt;
			this.volumen = volumen;
			this.cognacart = cognacart;
		}
		
		
		/**
		 * getter
		 * @return image 
		 */
		public String getImage(){
			return this.image;
		}
		
		/**
		 * setter
		 * @param Image
		 */
		public void setImage(String Image){
			
			this.image=Image;
		}
		/** 
		 * getter
		 * @return Alter
		 */
		public String getAlter(){
			return this.alter;
		}
		
		/**
		 * setter
		 * @param Alter
		 */
		public void setAlter(String Alter){
			
			this.alter=Alter;
		}

		/**
		 * Alkoholgehalt
		 * @return alkoholgehalt
		 */
		public String getAlkoholgehalt(){
			return this.alkoholgehalt;
		}
		
		/**
		 * setter
		 * @param Alkoholgehalt
		 */
		public void setAlkoholgehalt(String Alkoholgehalt){
			
			this.alkoholgehalt=Alkoholgehalt;
		}
		
		/**
		 * getter
		 * @return volumen
		 */
		public double getVolumen(){
			return this.volumen;
		}
		
		/**
		 * setter
		 * @param Volumen
		 */
		public void setVolumen(double Volumen){
			
			this.volumen=Volumen;
		}
	
		/**
		 * getter
		 * @return cogancart
		 */
		public String getCognacart(){
			return this.cognacart;
		}
		
		/**
		 * setter
		 * @param Cognacart
		 */
		public void setCognacart(String Cognacart){
			
			this.cognacart=Cognacart;
		}

		/**
		 * @param id
		 */
		public void delete(Long id) {
			
		}
}
