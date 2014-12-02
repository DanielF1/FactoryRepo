package videoshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import videoshop.model.NormalUserRepository;

@Controller
@PreAuthorize("hasRole('ROLE_BOSS')")
class AdminController {

	private final NormalUserRepository customerRepository;

	@Autowired
	public AdminController(NormalUserRepository customerRepository) {

		this.customerRepository = customerRepository;
	}

	@RequestMapping("/customers")
	public String customers(ModelMap modelMap) {

		modelMap.addAttribute("customerList", customerRepository.findAll());

		return "customers";
	}
}
