package videoshop;

//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.PostConstruct;

//import kickstart.model.Barrel;
//import kickstart.model.Barrel.BarrelType;
//import kickstart.model.BarrelStock;
//import kickstart.model.Bottle;
//import kickstart.model.Bottle.BottleType;
//import kickstart.model.BottleStock;
//import kickstart.model.CookBook;
//import kickstart.model.Ingredient;
//import kickstart.model.Recipe;
//import kickstart.model.Stock;
//import kickstart.model.Stock2;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.salespointframework.Salespoint;
import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.SalespointWebConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import videoshop.model.Barrel;
import videoshop.model.Barrel.BarrelType;
import videoshop.model.BarrelStock;
import videoshop.model.CookBook;
import videoshop.model.Ingredient;
import videoshop.model.Recipe;
import videoshop.model.Stock;
import videoshop.model.Stock2;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = { Salespoint.class, CognacFactory.class })
@EnableJpaRepositories(basePackageClasses = { Salespoint.class, CognacFactory.class })



@Import({ SalespointWebConfiguration.class })
@ComponentScan
public class CognacFactory {

	public static void main(String[] args) {
		SpringApplication.run(CognacFactory.class, args);
	}

	@Configuration
	static class WebConfiguration extends SalespointWebConfiguration {

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/login").setViewName("login");
		}
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	static class WebSecurityConfiguration extends SalespointSecurityConfiguration {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable();

			http.authorizeRequests().antMatchers("/**").permitAll().and().//
					formLogin().loginPage("/login").loginProcessingUrl("/login").and(). //
					logout().logoutUrl("/logout").logoutSuccessUrl("/");
		}
	}
	
//	SERGEJ'S PART
	
	@Autowired CookBook cookbook;
	@Autowired Stock stock;
	@Autowired Stock2 stock2;
	
	@PostConstruct
	private void initializeCookBook() 
	{
		
		Ingredient i1 = new Ingredient("Destillat A", 2000, "Liter");
		Ingredient i2 = new Ingredient("Destillat B", 200, "Liter");
		Ingredient i3 = new Ingredient("Destillat E", 100, "Liter");
		
		List<Ingredient> mapIngredients1 = new ArrayList<Ingredient>();
		
		mapIngredients1.add(i1);
		mapIngredients1.add(i2);
		mapIngredients1.add(i3);
		
		cookbook.save(new Recipe("Cognac A", mapIngredients1));
		
		Ingredient i4 = new Ingredient("Destillat B", 140, "Liter");
		Ingredient i5 = new Ingredient("Destillat T", 20, "Liter");
		Ingredient i6 = new Ingredient("Destillat H", 10, "Liter");
		
		List<Ingredient> mapIngredients2 = new ArrayList<Ingredient>();
		
		mapIngredients2.add(i4);
		mapIngredients2.add(i5);
		mapIngredients2.add(i6);
		
		cookbook.save(new Recipe("Cognac B", mapIngredients2));
		
		Ingredient i7 = new Ingredient("Destillat A", 140, "Liter");
		Ingredient i8 = new Ingredient("Destillat C", 20, "Liter");
		Ingredient i9 = new Ingredient("Destillat D", 10, "Liter");
		
		List<Ingredient> mapIngredients3 = new ArrayList<Ingredient>();
		
		mapIngredients3.add(i7);
		mapIngredients3.add(i8);
		mapIngredients3.add(i9);
		
		cookbook.save(new Recipe("Cognac C", mapIngredients3));		
	}
	
	@PostConstruct
	private void initializeStock() 
	{

		Barrel b1 = new Barrel("Destillat A", 200, BarrelType.FULL);
		Barrel b2 = new Barrel("Destillat B", 40, BarrelType.FULL);
		Barrel b3 = new Barrel("Destillat D", 240, BarrelType.FULL);
		Barrel b4 = new Barrel("Destillat C", 170, BarrelType.FULL);
		Barrel b5 = new Barrel("Destillat E", 70, BarrelType.FULL);
		
		List<Barrel> mapBarrels = new ArrayList<Barrel>();
		
		mapBarrels.add(b1);
		mapBarrels.add(b2);
		mapBarrels.add(b3);
		mapBarrels.add(b4);
		mapBarrels.add(b5);
		
		stock.save(new BarrelStock("Lager A", mapBarrels));
	
	}
	
	
	
//	@PostConstruct
//	void initializeStock2() 
//	{
//
//		Bottle b1 = new Bottle(0.7, BottleType.EMPTY);
//		Bottle b2 = new Bottle(0.7, BottleType.FULL);
//		
//		List<Bottle> empty = new ArrayList<Bottle>();
//		List<Bottle> full = new ArrayList<Bottle>();
//		
//		empty.add(b1);
//		full.add(b2);
//		
//		stock2.save(new BottleStock("Lager A", 100, empty, 20, full));
//	}
}
