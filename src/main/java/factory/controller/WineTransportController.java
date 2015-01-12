package factory.controller;

import java.util.ArrayList;
import java.util.List;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import factory.model.ArticleRepository;
import factory.model.BarrelStock;
import factory.model.BottleStock;
import factory.model.FoundLocation;
import factory.model.Ingredient;
import factory.model.WineStock;
import factory.repository.BarrelTransportRepository;
import factory.repository.CookBookRepository;
import factory.repository.DepartmentRepository;
import factory.repository.LocationRepository;

@Controller
public class WineTransportController {

	private final LocationRepository locationRepository;
	private final DepartmentRepository departmentrepository;
	
	@Autowired 
	public WineTransportController(LocationRepository locationRepository, DepartmentRepository departmentrepository)
	{
		this.locationRepository = locationRepository;
		this.departmentrepository = departmentrepository;
	}
	
	@RequestMapping(value = "/wineTransport")
	public String wineTransport(Model model)
	{
		model.addAttribute("wineStocks", locationRepository.findAll());
		return "wineTransport";
	}
}
