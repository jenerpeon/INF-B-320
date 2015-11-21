package internetkaufhaus.controller;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.Date;

=======
import java.util.Date;
>>>>>>> articlemanagement
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
<<<<<<< HEAD
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
>>>>>>> articlemanagement

import internetkaufhaus.model.Comment;
import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.ConcreteProductRepository;
import internetkaufhaus.model.search;

@Controller
public class CatalogController {
	private static final Quantity NONE = Quantity.of(0);
	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
<<<<<<< HEAD
	private final ConcreteProductRepository concreteCatalog;
	private final search prodSearch;

	@Autowired
	public CatalogController(Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, search prodSearch, ConcreteProductRepository concreteCatalog) {
		this.catalog = catalog;
		this.inventory = inventory;
		this.prodSearch = prodSearch;
		this.concreteCatalog = concreteCatalog;

	}
	@RequestMapping("/sufu/{pagenumber}")
	public String sufu(@RequestParam("search") String lookup, @PathVariable("pagenumber") int number, ModelMap model) {

		int max_number = prodSearch.list50(prodSearch.lookup_bar(lookup)).size() + 1;
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("prods", prodSearch.list50(prodSearch.lookup_bar(lookup)).get(number - 1));
		model.addAttribute("numbers", IntStream.range(1, max_number).boxed().collect(Collectors.toList()));
		model.addAttribute("search", lookup);

		return "catalog";
	}
	@RequestMapping("/sufu/{search}/{pagenumber}")
	public String postsufu(@PathVariable("search") String lookup, @PathVariable("pagenumber") int number, ModelMap model) {

		int max_number = prodSearch.list50(prodSearch.lookup_bar(lookup)).size() + 1;
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("prods", prodSearch.list50(prodSearch.lookup_bar(lookup)).get(number - 1));
		model.addAttribute("numbers", IntStream.range(1, max_number).boxed().collect(Collectors.toList()));
		model.addAttribute("search", lookup);

		return "catalog";
	}

	@RequestMapping("/catalog/{type}")
	public String category(@PathVariable("type") String category, ModelMap model) {

		model.addAttribute("category", category);
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("ProdsOfCategory", prodSearch.getProdsByCategory(category));

		return "catalog";
	}

	@RequestMapping(path = "/catalog/{type}/{split}/{pagenumber}", method = {RequestMethod.POST, RequestMethod.GET})
	public String list50(Pageable pagable, @RequestParam(value = "total", defaultValue = "0") Integer total, @PathVariable("type") String category, @PathVariable("split") int split, @PathVariable("pagenumber") int number,
			ModelMap model) {
		if(split==0)
            split = 3;
		if(total!=0)
            split = total;
        Page page;
		model.addAttribute("category", category);
		model.addAttribute("number", number);
		model.addAttribute("split", split);
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("prods", page = concreteCatalog.findByCategory(category, new PageRequest(number-1,split)));
		model.addAttribute("numbers", IntStream.range(1, page.getTotalPages()+1).boxed().collect(Collectors.toList()));
=======
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
	public String list50(@PathVariable("type") String category, @PathVariable("pagenumber") int number,
			ModelMap model) {
		int max_number = prodSearch.list50(category).size() + 1;
		model.addAttribute("category", category);
		model.addAttribute("categories", prodSearch.getCagegories());

		System.out.print(prodSearch.list50(category).toString());

		model.addAttribute("prod50", prodSearch.list50(category).get(number - 1));
		// model.addAttribute("prod50",
		// prodSearch.getProdsByCategory(category));
		model.addAttribute("numbers", IntStream.range(1, max_number).boxed().collect(Collectors.toList()));
>>>>>>> articlemanagement
		return "catalog";
	}

	@RequestMapping("/detail/{prodId}")
	public String detail(@PathVariable("prodId") ConcreteProduct prod, Model model) {

		Optional<InventoryItem> item = inventory.findByProductIdentifier(prod.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
<<<<<<< HEAD
=======

>>>>>>> articlemanagement
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));
<<<<<<< HEAD
		model.addAttribute("comments", prod.getComments());
=======
		model.addAttribute("comments", prod.getReviewedComments());
>>>>>>> articlemanagement

		return "detail";
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("prodId") ConcreteProduct prod, @RequestParam("comment") String comment,
			@RequestParam("rating") int rating, Model model) {
<<<<<<< HEAD
		Comment c = new Comment(comment, rating, new Date(), "");
		if (!(comment == "")) {
			prod.addComment(c);
=======
		Comment c = new Comment(comment, rating, new Date(),"");
		if (!(comment == "")) {
			prod.getNewComments().add(c);
>>>>>>> articlemanagement
			c.setFormatedDate(c.getDate());
			catalog.save(prod);
			model.addAttribute("time", c.getFormatedDate());
		}
		return "redirect:detail/" + prod.getIdentifier();
<<<<<<< HEAD

=======
>>>>>>> articlemanagement
	}

}
