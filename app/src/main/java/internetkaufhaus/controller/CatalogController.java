package internetkaufhaus.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
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
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import internetkaufhaus.entities.Comment;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.model.ConcreteMailSender;
import internetkaufhaus.model.NewsletterManager;
import internetkaufhaus.model.Search;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

/**
 * This is the catalog controller. It controls the catalog. Or does it catalog the controller? You never know...
 * In this class you may find the controllers for the catalog and artible pages, should you choose to look for them.
 * @author max
 *
 */
@Controller
public class CatalogController {
	private static final Quantity NONE = Quantity.of(0);
	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
	private final ConcreteProductRepository concreteCatalog;
	private final Search prodSearch;
	private final NewsletterManager newsManager;
	private final MailSender sender;
	private final ConcreteUserAccountRepository usermanager;

	/**
	 * This is the constructor. It's neither used nor does it contain any functionality other than storing function arguments as class attribute, what do you expect me to write here?
	 * 
	 * @param catalog
	 * @param inventory
	 * @param prodSearch
	 * @param concreteCatalog
	 * @param newsManager
	 * @param sender
	 * @param usermanager
	 */
	@Autowired
	public CatalogController(Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, Search prodSearch, ConcreteProductRepository concreteCatalog, NewsletterManager newsManager, MailSender sender, ConcreteUserAccountRepository usermanager) {

		this.catalog = catalog;
		this.inventory = inventory;
		this.prodSearch = prodSearch;
		this.concreteCatalog = concreteCatalog;
		this.newsManager = newsManager;
		this.sender = sender;
		this.usermanager = usermanager;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param lookup
	 * @param number
	 * @param model
	 * @return
	 */
	@RequestMapping("/sufu/{pagenumber}")
	public String sufu(@RequestParam("search") String lookup, @PathVariable("pagenumber") int number, ModelMap model) {

		int max_number = prodSearch.list50(prodSearch.lookup_bar(lookup)).size() + 1;
		model.addAttribute("prods", prodSearch.list50(prodSearch.lookup_bar(lookup)).get(number - 1));
		model.addAttribute("numbers", IntStream.range(1, max_number).boxed().collect(Collectors.toList()));
		model.addAttribute("search", lookup);
		return "catalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param lookup
	 * @param number
	 * @param model
	 * @return
	 */
	@RequestMapping("/sufu/{search}/{pagenumber}")
	public String postsufu(@PathVariable("search") String lookup, @PathVariable("pagenumber") int number, ModelMap model) {

		int max_number = prodSearch.list50(prodSearch.lookup_bar(lookup)).size() + 1;
		// model.addAttribute("prods", concreteCatalog));
		model.addAttribute("prods", prodSearch.list50(prodSearch.lookup_bar(lookup)).get(number - 1));
		model.addAttribute("numbers", IntStream.range(1, max_number).boxed().collect(Collectors.toList()));
		model.addAttribute("search", lookup);
		return "catalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param category
	 * @param model
	 * @return
	 */
	@RequestMapping("/catalog/{type}")
	public String category(@PathVariable("type") String category, ModelMap model) {

		model.addAttribute("category", category);
		model.addAttribute("ProdsOfCategory", prodSearch.getProdsByCategory(category));

		return "catalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param category
	 * @param split
	 * @param number
	 * @param representation
	 * @param sort
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/catalog/{type}/{sort}/{representation}/{split}/{pagenumber}", method = { RequestMethod.POST, RequestMethod.GET })
	public String list50(@PathVariable("type") String category, @PathVariable("split") int split, @PathVariable("pagenumber") int number, @PathVariable("representation") int representation, @PathVariable("sort") String sort, ModelMap model) {
		if (split == 0) {
			split = 3;
		}

		Sort sorting = null;

		switch (sort) {
		case "selled":
			sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "selled", Sort.NullHandling.NATIVE), new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
			break;
		case "rating":
			sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "averageRating", Sort.NullHandling.NATIVE), new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
			break;
		case "name,asc":
			sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
			break;
		case "name,desc":
			sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "name", Sort.NullHandling.NATIVE));
			break;
		case "price,asc":
			sorting = new Sort(Arrays.asList(new Sort.Order(Sort.Direction.ASC, "price", Sort.NullHandling.NATIVE), new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE)));
			break;
		case "price,desc":
			sorting = new Sort(Arrays.asList(new Sort.Order(Sort.Direction.DESC, "price", Sort.NullHandling.NATIVE), new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE)));
			break;
		default:
			sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
		}

		Page<ConcreteProduct> page = concreteCatalog.findByCategory(category, new PageRequest(number - 1, split, sorting));
		List<ConcreteProduct> prods = page.getContent();

		List<Integer> numbers = IntStream.range(1, page.getTotalPages() + 1).boxed().collect(Collectors.toList());
		if (number == 0) {
			number = 1;
		}
		if (number > numbers.size()) {
			number = numbers.size();
		}
		model.addAttribute("category", category);
		model.addAttribute("number", number);
		Set<Integer> quantities = Sets.newSet(split, 3, 6, 9, 18, 45, 90, 180, prodSearch.getProdsByCategory(category).size());
		quantities.removeIf(i -> i > prodSearch.getProdsByCategory(category).size());
		model.addAttribute("maximum", prodSearch.getProdsByCategory(category).size());
		model.addAttribute("quantities", new TreeSet<Integer>(quantities));
		model.addAttribute("sort", sort);
		model.addAttribute("representation", representation);
		model.addAttribute("split", split);
		model.addAttribute("prods", prods);
		model.addAttribute("numbers", IntStream.range(1, page.getTotalPages() + 1).boxed().collect(Collectors.toList()));
		return "catalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param pagable
	 * @param category
	 * @param number
	 * @param split
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/catalog/{type}/{split}/{pagenumber}/changedSetting", method = RequestMethod.POST)
	public String changeStartPageSetting(@PathVariable("type") String category, @PathVariable("pagenumber") int number, @RequestParam("total") int split) {
		return "redirect:/catalog/" + category + '/' + split + '/' + number;
		/*
		 * model.addAttribute("prods", page); model.addAttribute("numbers", numbers); model.addAttribute("sites", numbers.size()); model.addAttribute("categories", prodSearch.getCategories()); return "catalog";
		 */
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param pagable
	 * @param category
	 * @param number
	 * @param split
	 * @param sort
	 * @param representation
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/catalog/{type}/{sort}/{representation}/{split}/{pagenumber}/changedSetting", method = RequestMethod.POST)
	public String changeStartPageSetting(@PathVariable("type") String category, @PathVariable("pagenumber") int number, @RequestParam("total") int split, @RequestParam(value = "sort", defaultValue = "name") String sort, @PathVariable("representation") int representation, ModelMap model) {
		return "redirect:/catalog/" + category + '/' + sort + '/' + representation + '/' + split + '/' + number;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param prod
	 * @param model
	 * @return
	 */
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

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param prod
	 * @param comment
	 * @param rating
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("prodId") ConcreteProduct prod, @RequestParam("comment") String comment, @RequestParam("rating") int rating, Model model, @LoggedIn Optional<UserAccount> user) {
		Comment c = new Comment(comment, rating, new Date(), "");
		if (!(comment.equals("")) && user.isPresent()) {
			c.setFormatedDate(c.getDate());
			prod.addComment(c, usermanager.findByUserAccount(user.get()));
			catalog.save(prod);
			model.addAttribute("time", c.getFormatedDate());
		}
		return "redirect:detail/" + prod.getIdentifier();
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param sendTo
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/newsletter", method = RequestMethod.GET)
	public String newsletter(@RequestParam("email") String sendTo, ModelMap model) throws ParseException {
		ConcreteMailSender concreteMailSender = new ConcreteMailSender(sender);
		String text = "Sie haben sich für den Woods Super Dooper Shop Newsletter angemeldet.";
		String username;

		if (usermanager.findByEmail(sendTo) == null) {
			username = "Nicht registierter Abonnet";
		} else {
			username = usermanager.findByEmail(sendTo).getUserAccount().getUsername();
		}
		newsManager.getMap().put(username, sendTo);
		concreteMailSender.sendMail(sendTo, text, "zu@googlemail.com", "NewsletterAbonnement");

		model.addAttribute("prodList", catalog.findAll());

		return "index";

	}
}
