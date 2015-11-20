package internetkaufhaus.controller;

import java.util.Optional;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.search;

@Controller
@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
public class ManagementController {

	private static final Quantity NONE = Quantity.of(0);
	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
	private final search prodSearch;

	@Autowired
	public ManagementController(Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, search prodSearch) {
		this.catalog = catalog;
		this.inventory = inventory;
		this.prodSearch = prodSearch;

	}

	@RequestMapping("/management")
	public String articleManagement(Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("prod50", catalog.findAll());
		model.addAttribute("categories", prodSearch.getCagegories());
		return "changecatalog";
	}

	@RequestMapping("/management/addArticle")
	public String addArticle(Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("categories", prodSearch.getCagegories());
		return "changecatalognewitem";
	}

	@RequestMapping("/management/editArticle/{prodId}")
	public String editArticle(@PathVariable("prodId") ConcreteProduct prod, Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("price", prod.getPrice().getNumber());
		return "changecatalogchangeitem";
	}

	@RequestMapping("/management/deleteArticle/{prodId}")
	public String deleteArticle(@PathVariable("prodId") ConcreteProduct prod, Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("price", prod.getPrice().getNumber());
		return "changecatalogdeleteitem";
	}

	public Inventory<InventoryItem> getInventory() {
		return inventory;
	}

	public static Quantity getNone() {
		return NONE;
	}

}
