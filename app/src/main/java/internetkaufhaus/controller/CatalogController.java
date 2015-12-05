package internetkaufhaus.controller;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.mockito.internal.util.collections.Sets;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import internetkaufhaus.model.Comment;
import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.ConcreteProductRepository;
import internetkaufhaus.model.ConcreteUserAccountRepository;
import internetkaufhaus.model.Search;

@Controller
public class CatalogController {
	private static final Quantity NONE = Quantity.of(0);
	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
	private final ConcreteProductRepository concreteCatalog;
	private final Search prodSearch;
	private final ConcreteUserAccountRepository concreteManager;

	@Autowired
	public CatalogController(ConcreteUserAccountRepository concreteManager,Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, Search prodSearch, ConcreteProductRepository concreteCatalog) {
		this.catalog = catalog;
		this.inventory = inventory;
		this.prodSearch = prodSearch;
		this.concreteCatalog = concreteCatalog;
        this.concreteManager = concreteManager;

	}

	@RequestMapping("/sufu/{pagenumber}")
	public String sufu(@RequestParam("search") String lookup, @PathVariable("pagenumber") int number, ModelMap model) {

		int max_number = prodSearch.list50(prodSearch.lookup_bar(lookup)).size() + 1;
		model.addAttribute("prods", prodSearch.list50(prodSearch.lookup_bar(lookup)).get(number - 1));
		model.addAttribute("numbers", IntStream.range(1, max_number).boxed().collect(Collectors.toList()));
		model.addAttribute("search", lookup);

		return "catalog";
	}

	@RequestMapping("/sufu/{search}/{pagenumber}")
	public String postsufu(@PathVariable("search") String lookup, @PathVariable("pagenumber") int number, ModelMap model) {

		int max_number = prodSearch.list50(prodSearch.lookup_bar(lookup)).size() + 1;
		//model.addAttribute("prods", concreteCatalog));
		model.addAttribute("prods", prodSearch.list50(prodSearch.lookup_bar(lookup)).get(number - 1));
		model.addAttribute("numbers", IntStream.range(1, max_number).boxed().collect(Collectors.toList()));
		model.addAttribute("search", lookup);

		return "catalog";
	}

	@RequestMapping("/catalog/{type}")
	public String category(@PathVariable("type") String category, ModelMap model) {

		model.addAttribute("category", category);
		model.addAttribute("ProdsOfCategory", prodSearch.getProdsByCategory(category));

		return "catalog";
	}
	
	@RequestMapping(path = "/catalog/{type}/{split}/{pagenumber}", method = { RequestMethod.POST, RequestMethod.GET })
	public String list50(Pageable pagable, @RequestParam(value = "total", defaultValue = "0") Integer total, @PathVariable("type") String category, @PathVariable("split") int split, @PathVariable("pagenumber") int number, ModelMap model) {
		if (split == 0)
			split = 3;
		if (total != 0)
			split = total;
		Page page;
		model.addAttribute("category", category);
		model.addAttribute("number", number);
		Set<Integer> quantities = Sets.newSet(split, 2, 5, 10, 20, 50, 100, 200, prodSearch.getProdsByCategory(category).size());
		quantities.removeIf(i -> i > prodSearch.getProdsByCategory(category).size());
		model.addAttribute("maximum", prodSearch.getProdsByCategory(category).size());
		model.addAttribute("quantities", new TreeSet<Integer>(quantities));
		model.addAttribute("split", split);
		model.addAttribute("prods", page = concreteCatalog.findByCategory(category, new PageRequest(number-1,split)));
		model.addAttribute("numbers", IntStream.range(1, page.getTotalPages()+1).boxed().collect(Collectors.toList()));
		return "catalog";
	}

	@RequestMapping(value = "/catalog/{type}/{split}/{pagenumber}/changedSetting", method = RequestMethod.POST)
	public String changeStartPageSetting(@PathVariable("type") String category, @PathVariable("pagenumber") int number, @RequestParam("total") int split) {
		return "redirect:/catalog/" + category + '/' + split + '/' + number;
	}

	@RequestMapping("/detail/{prodId}")
	public String detail(@PathVariable("prodId") ConcreteProduct prod, Model model) {

		Optional<InventoryItem> item = inventory.findByProductIdentifier(prod.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));
		model.addAttribute("comments", prod.getAcceptedComments());

		return "detail";
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("prodId") ConcreteProduct prod, @RequestParam("comment") String comment, @RequestParam("rating") int rating, Model model, @LoggedIn Optional<UserAccount> user) {
		Comment c = new Comment(comment, rating, new Date(), "");
		if (!(comment == "") && user.isPresent()) {
			c.setFormatedDate(c.getDate());
			prod.addComment(c,concreteManager.findByUserAccount(user.get()));
			catalog.save(prod);
			model.addAttribute("time", c.getFormatedDate());
		}
		return "redirect:detail/" + prod.getIdentifier();
	}

}
