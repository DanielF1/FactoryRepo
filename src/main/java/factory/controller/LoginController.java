package factory.controller;

import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;


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
}
