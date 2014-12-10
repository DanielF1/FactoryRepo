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


@Controller
@PreAuthorize("hasRole('ROLE_SALESMAN')")
public class Verkaeufercontroller {
	
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	
	@Autowired
	public Verkaeufercontroller(OrderManager<Order> orderManager, Inventory<InventoryItem> inventory) {

		this.orderManager = orderManager;
		this.inventory = inventory;
	
	}
	
	@RequestMapping("/orders")
	public String orders(ModelMap modelMap) {

		modelMap.addAttribute("ordersCompleted", orderManager.find(OrderStatus.COMPLETED));

		return "orders";
	}


}
