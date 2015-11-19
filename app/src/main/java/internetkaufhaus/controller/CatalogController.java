package internetkaufhaus.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;
import java.util.HashMap;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.inventory.*;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;

import internetkaufhaus.model.Comment;
import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.search;

@Controller
public class CatalogController {
	private static final Quantity NONE = Quantity.of(0);
	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
	private final search prodSearch;

	@Autowired
	public CatalogController(Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, search prodSearch) {
		this.catalog = catalog;
		this.inventory = inventory;
		this.prodSearch = prodSearch;

	}

	@RequestMapping("/catalog/{type}")
	public String category(@PathVariable("type") String category, ModelMap model) {
		model.addAttribute("category", category);
		model.addAttribute("categories", prodSearch.getCagegories());

		model.addAttribute("ProdsOfCategory", prodSearch.getProdsByCategory(category));
		System.out.println(prodSearch.getProdsByCategory(category));
		return "catalog";
	}

	@RequestMapping(path = "/catalog/{type}/{pagenumber}")
	public String list50(@PathVariable("type") String category, @PathVariable("pagenumber") int number, ModelMap model) {
		int max_number = prodSearch.list50(category).size() + 1;
		model.addAttribute("category", category);
		model.addAttribute("categories", prodSearch.getCagegories());

		System.out.print(prodSearch.list50(category).toString());

		model.addAttribute("prod50", prodSearch.list50(category).get(number - 1));
		// model.addAttribute("prod50",
		// prodSearch.getProdsByCategory(category));
		model.addAttribute("numbers", IntStream.range(1, max_number).boxed().collect(Collectors.toList()));
		return "catalog";
	}

	@RequestMapping("/detail/{prodId}")
	public String detail(@PathVariable("prodId") ConcreteProduct prod, Model model) {

		Optional<InventoryItem> item = inventory.findByProductIdentifier(prod.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);

		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));
		model.addAttribute("comments", prod.getComments());

		return "detail";
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("prodId") ConcreteProduct prod, @RequestParam("comment") String comment, @RequestParam("rating") int rating, Model model) {
		Comment c = new Comment(comment, rating, new Date(), "");
		if (!(comment == "")) {
			prod.addComment(c);
			c.setFormatedDate(c.getDate());
			catalog.save(prod);
			model.addAttribute("time", c.getFormatedDate());
		}
		return "redirect:detail/" + prod.getIdentifier();
	}

}
