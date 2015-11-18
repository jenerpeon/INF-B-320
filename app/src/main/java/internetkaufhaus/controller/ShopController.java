package internetkaufhaus.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import internetkaufhaus.model.*;
import org.salespointframework.useraccount.*;
import org.salespointframework.catalog.Catalog;
import internetkaufhaus.model.search;
@Controller
public class ShopController {

	private final Catalog<ConcreteProduct> catalog;
	private final UserAccountManager userAccountManager;
  private final search prodSearch;

	@Autowired
	public ShopController(UserAccountManager userAccountManager, Catalog<ConcreteProduct> catalog, search prodSearch) {

		this.userAccountManager = userAccountManager;
		this.catalog = catalog;
		this.prodSearch = prodSearch;
	}


	@RequestMapping(value = {"/", "/index"})
	public String index(ModelMap modelmap) {
	
        modelmap.addAttribute("prodList", catalog.findAll());
        modelmap.addAttribute("categories", prodSearch.getCagegories());
        
        return "index";
	}
	


}
