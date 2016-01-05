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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import internetkaufhaus.entities.Comment;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.model.Search;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.NewsletterService;

/**
 * This is the catalog controller. It controls the catalog. Or does it catalog
 * the controller? You never know... In this class you may find the controllers
 * for the catalog and article pages, should you choose to look for them.
 * 
 * @author max
 *
 */
@Controller
@SessionAttributes("cart")
public class CatalogController {
	private static final Quantity NONE = Quantity.of(0);
	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
	private final ConcreteProductRepository concreteCatalog;
	private final Search prodSearch;
	private final NewsletterService newsManager;
	private final ConcreteMailService sender;
	private final ConcreteUserAccountRepository usermanager;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
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
	public CatalogController(Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, Search prodSearch,
			ConcreteProductRepository concreteCatalog, NewsletterService newsManager, ConcreteMailService sender,
			ConcreteUserAccountRepository usermanager) {

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
	 * This page shows a particular search result page as defined by the search
	 * string and the pagenumber.
	 * 
	 * @param lookup
	 *            the search string as entered by the user
	 * @param number
	 *            the page number the user requested
	 * @param model
	 * @return
	 */
	@RequestMapping("/sufu")
	public String sufu(@RequestParam("search") String lookup) {
		return "redirect:/sufu/" + lookup;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows a particular search result page as defined by the search
	 * string and the pagenumber.
	 * 
	 * @param lookup
	 *            the search string as entered by the user
	 * @param number
	 *            the page number the user requested
	 * @param model
	 * @return
	 */
	@RequestMapping("/sufu/{search}")
	public String postsufu(@PathVariable("search") String lookup, ModelMap model) {
		try {
			int split = 10;
			model.addAttribute("prods", prodSearch.lookup_bar(lookup, 20));
			model.addAttribute("search", lookup);
			model.addAttribute("representation", 1);
		} catch (Exception e) {
			System.out.println("sufu stage 2:" + e.toString());
			return "index";
		}
		return "catalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows all products of a certain category. This is deprecated.
	 * TODO: Remove this(?)
	 * 
	 * @param category
	 *            the category which the user wants to see.
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
	 * Shows the requested catalog page as defined by category, number of items
	 * per page (givenSplit), number of current page (givenNumber), display
	 * style (representation) and order of sorting (sort).
	 * 
	 * @param category
	 *            the category to display
	 * @param givenSplit
	 *            the number of items per page
	 * @param givenNumber
	 *            the page number
	 * @param representation
	 *            which page design to use
	 * @param sort
	 *            the order to sort the items in
	 * @param model
	 * @return
	 */
	@RequestMapping(path = "/catalog/{type}/{sorting}/{representation}/{split}/{pagenumber}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String list50(@PathVariable("type") String category, @PathVariable("split") int givenSplit,
			@PathVariable("pagenumber") int givenNumber, @PathVariable("representation") int representation,
			@PathVariable("sorting") String sort, ModelMap model) {
		int split;
		if (givenSplit == 0) {
			split = 3;
		} else {
			split = givenSplit;
		}

		Sort sorting = null;

		switch (sort) {
		case "popularity":
			sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "amountProductsSold", Sort.NullHandling.NATIVE),
					new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
			break;
		case "rating":
			sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "averageRating", Sort.NullHandling.NATIVE),
					new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
			break;
		case "name,asc":
			sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
			break;
		case "name,desc":
			sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "name", Sort.NullHandling.NATIVE));
			break;
		case "price,asc":
			sorting = new Sort(Arrays.asList(new Sort.Order(Sort.Direction.ASC, "price", Sort.NullHandling.NATIVE),
					new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE)));
			break;
		case "price,desc":
			sorting = new Sort(Arrays.asList(new Sort.Order(Sort.Direction.DESC, "price", Sort.NullHandling.NATIVE),
					new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE)));
			break;
		default:
			sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
		}

		Page<ConcreteProduct> page = concreteCatalog.findByCategory(category,
				new PageRequest(givenNumber - 1, split, sorting));
		List<ConcreteProduct> prods = page.getContent();

		List<Integer> numbers = IntStream.range(1, page.getTotalPages() + 1).boxed().collect(Collectors.toList());
		int number;
		if (givenNumber > numbers.size()) {
			number = numbers.size();
		} else if (givenNumber == 0) {
			number = 1;
		} else {
			number = givenNumber;
		}
		model.addAttribute("category", category);
		model.addAttribute("number", number);
		Set<Integer> quantities = Sets.newSet(split, 2, 3, 4, 5, 10, 15, 25, 50, 100, 150, 250, 500,
				prodSearch.getProdsByCategory(category).size());
		quantities.removeIf(i -> i > prodSearch.getProdsByCategory(category).size());
		model.addAttribute("maximum", prodSearch.getProdsByCategory(category).size());
		model.addAttribute("quantities", new TreeSet<Integer>(quantities));
		model.addAttribute("sort", sort);
		model.addAttribute("representation", representation);
		model.addAttribute("split", split);
		model.addAttribute("prods", prods);
		model.addAttribute("numbers",
				IntStream.range(1, page.getTotalPages() + 1).boxed().collect(Collectors.toList()));
		return "catalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * TODO: Is this deprecated?
	 * 
	 * @param pagable
	 * @param category
	 * @param number
	 * @param split
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/catalog/{type}/{split}/{pagenumber}/changedSetting", method = RequestMethod.POST)
	public String changeStartPageSetting(@PathVariable("type") String category, @PathVariable("pagenumber") int number,
			@RequestParam("total") int split) {
		return "redirect:/catalog/" + category + '/' + split + '/' + number;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps? For
	 * further documentation of this please refer to the function list50 in the
	 * CatalogController, to which this redirects.
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
	public String changeStartPageSetting(@PathVariable("type") String category, @PathVariable("pagenumber") int number,
			@RequestParam("total") int split, @RequestParam(value = "sort", defaultValue = "name") String sort,
			@PathVariable("representation") int representation) {
		return "redirect:/catalog/" + category + '/' + sort + '/' + representation + '/' + split + '/' + number;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the article page for a certain article as given by its
	 * ID.
	 * 
	 * @param prod
	 *            the article ID of the article to display
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
	 * This page submits a comment to an article and then redirects to the
	 * corresponding article page.
	 * 
	 * @param prod
	 *            the article ID of the article to comment on
	 * @param comment
	 *            the actual comment text
	 * @param rating
	 *            the rating associated with the comment
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("prodId") ConcreteProduct prod, @RequestParam("comment") String comment,
			@RequestParam("rating") int rating, Model model, @LoggedIn Optional<UserAccount> user) {
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
	 * This page registers a given user to the E-Mail-newsletter and sends a
	 * confirmation E-Mail. It then redirects the user to the start page.
	 * 
	 * @param sendTo
	 *            the E-Mail-Address to send the newsletter to.
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/newsletter", method = RequestMethod.GET)
	public String newsletter(@RequestParam("email") String sendTo, ModelMap model) throws ParseException {
		String text = "Sie haben sich für den Woods Super Dooper Shop Newsletter angemeldet.";
		String username;

		if (usermanager.findByEmail(sendTo) == null) {
			username = "Nicht registierter Abonnet";
		} else {
			username = usermanager.findByEmail(sendTo).getUserAccount().getUsername();
		}
		newsManager.getMap().put(username, sendTo);
		sender.sendMail(sendTo, text, "zu@googlemail.com", "NewsletterAbonnement");
		model.addAttribute("prodList", catalog.findAll());
		return "index";

	}
}
