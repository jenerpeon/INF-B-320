package internetkaufhaus.controller;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import internetkaufhaus.entities.ConcreteProduct;

@Controller
public class ShopController {

	private final Catalog<ConcreteProduct> catalog;
	private final UserAccountManager userAccountManager;

	@Autowired
	public ShopController(UserAccountManager userAccountManager, Catalog<ConcreteProduct> catalog) {

		this.userAccountManager = userAccountManager;
		this.catalog = catalog;
	}

	@RequestMapping(value = { "/", "/index" })
	public String index(ModelMap model) {

		model.addAttribute("prodList", catalog.findAll());

		return "index";
	}

	public UserAccountManager getUserAccountManager() {
		return userAccountManager;
	}

}
