package internetkaufhaus.controller;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.model.Search;

@Controller
public class ShopController {

	private final Catalog<ConcreteProduct> catalog;
	private final UserAccountManager userAccountManager;
	private final Search prodSearch;

	@Autowired
	public ShopController(UserAccountManager userAccountManager, Catalog<ConcreteProduct> catalog, Search prodSearch) {

		this.userAccountManager = userAccountManager;
		this.catalog = catalog;
		this.prodSearch = prodSearch;
	}

	@RequestMapping(value = { "/", "/index" })
	public String index(ModelMap model) {

		model.addAttribute("prodList", catalog.findAll());
		model.addAttribute("categories", prodSearch.getCagegories());

		return "index";
	}

	public UserAccountManager getUserAccountManager() {
		return userAccountManager;
	}
	


}
