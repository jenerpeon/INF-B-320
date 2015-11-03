package internetkaufhaus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import internetkaufhaus.model.*;
import org.salespointframework.useraccount.*;

@Controller
public class ShopController {

	private final ProductCatalog catalog;
	private final UserAccountManager userAccountManager;

	@Autowired
	public ShopController(UserAccountManager userAccountManager, ProductCatalog catalog) {

		this.userAccountManager = userAccountManager;
		this.catalog = catalog;
	}


	@RequestMapping(value = {"/", "/index"})
	public String index(ModelMap modelmap) {
        modelmap.addAttribute("prodList", catalog.findAll());
        return "index";
	}
	


}
