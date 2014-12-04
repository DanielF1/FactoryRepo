package factory.controller;

import javax.validation.Valid;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import factory.model.validation.RegistrationForm;

@Controller
class LoginController {

	private final UserAccountManager userAccountManager;

	@Autowired
	public LoginController(UserAccountManager userAccountManager) {

		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");

		this.userAccountManager = userAccountManager;
	}

	@RequestMapping({ "/", "/index" })
	public String start() {
		return "index";
	}
	
	@RequestMapping("/registerNew")
	public String registerNew(@ModelAttribute("registrationForm") @Valid RegistrationForm registrationForm,
			BindingResult result) {

		if (result.hasErrors()) {
			return "register";
		}

		UserAccount userAccount = userAccountManager.create(registrationForm.getName(), registrationForm.getPassword(),
				new Role("ROLE_CUSTOMER"));
		userAccountManager.save(userAccount);

		return "redirect:/";
	}

	@RequestMapping("/register")
	public String register(ModelMap modelMap) {
		modelMap.addAttribute("registrationForm", new RegistrationForm());
		return "register";
	}
}
