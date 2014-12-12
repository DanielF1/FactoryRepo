package factory.controller;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import factory.model.CustomerRespository;


@Controller
@PreAuthorize("hasRole('ROLE_SALESMAN')")
public class Verkaeufercontroller {
	
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	private final CustomerRespository customerRepository;
	
	@Autowired
	public Verkaeufercontroller(OrderManager<Order> orderManager,
								Inventory<InventoryItem> inventory,
								CustomerRespository customerRepository) {

		this.orderManager = orderManager;
		this.inventory = inventory;
		this.customerRepository = customerRepository;
	
	}
	
	@RequestMapping("/orders")
	public String orders(ModelMap modelMap) {

		modelMap.addAttribute("ordersCompleted", orderManager.find(OrderStatus.COMPLETED));

		return "orders";
	}
	
	@RequestMapping(value = "/customerlist", method = RequestMethod.GET)
	public String customerlist(ModelMap modelMap) {

		modelMap.addAttribute("customerlist", customerRepository.findAll());

		return "customerlist";
	}


}
