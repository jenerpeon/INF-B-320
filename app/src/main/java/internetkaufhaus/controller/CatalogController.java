package internetkaufhaus.controller;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.mockito.internal.util.collections.Sets;
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

import com.google.common.collect.Iterators;

import internetkaufhaus.entities.Comment;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.DataService;
import internetkaufhaus.services.NewsletterService;

// TODO: Auto-generated Javadoc
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

	@Autowired
	private DataService dataService;

	/** The Constant NONE. */
	private static final Quantity NONE = Quantity.of(0);

	/** The news manager. */
	private final NewsletterService newsManager;

	/** The sender. */
	private final ConcreteMailService sender;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
	 *
	 * @param newsManager
	 *            the news manager
	 * @param sender
	 *            the sender
	 */
	@Autowired
	public CatalogController(NewsletterService newsManager, ConcreteMailService sender) {
		this.newsManager = newsManager;
		this.sender = sender;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows a particular search result page as defined by the search
	 * string and the pagenumber.
	 *
	 * @param lookup
	 *            the search string as entered by the user
	 * @return the string
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
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/sufu/{search}")
	public String postsufu(@PathVariable("search") String lookup, ModelMap model) {
		try {
			model.addAttribute("prods", dataService.getConcreteProductRepository().findByName(lookup));
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
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/catalog/{type}")
	public String category(@PathVariable("type") String category) {
		return "redirect:/catalog/" + category + '/' + "name,asc/1/6/1";
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
	 *            the model
	 * @return the string
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
			sorting = new Sort(Arrays.asList(new Sort.Order(Sort.Direction.ASC, "priceDecimal", Sort.NullHandling.NATIVE),
					new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE)));
			break;
		case "price,desc":
			sorting = new Sort(Arrays.asList(new Sort.Order(Sort.Direction.DESC, "priceDecimal", Sort.NullHandling.NATIVE),
					new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE)));
			break;
		default:
			sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
		}

		Page<ConcreteProduct> page = dataService.getConcreteProductRepository().findByCategory(category,
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
		
		int maxQuantity = Iterators.size(dataService.getConcreteProductRepository().findByCategory(category,  sorting).iterator());
		Set<Integer> quantities = Sets.newSet(split, 2, 3, 4, 5, 10, 15, 25, 50, 100, 150, 250, 500, maxQuantity);
		quantities.removeIf(i -> i > maxQuantity);
		model.addAttribute("maximum", maxQuantity);
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
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps? For
	 * further documentation of this please refer to the function list50 in the
	 * CatalogController, to which this redirects.
	 *
	 * @param category
	 *            the category
	 * @param number
	 *            the number
	 * @param split
	 *            the split
	 * @param sort
	 *            the sort
	 * @param representation
	 *            the representation
	 * @return the string
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
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/detail/{prodId}")
	public String detail(@PathVariable("prodId") ConcreteProduct prod, Model model) {
		Optional<InventoryItem> item = dataService.getInventory().findByProductIdentifier(prod.getIdentifier());
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
	 *            the model
	 * @param user
	 *            the user
	 * @return the string
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("prodId") ConcreteProduct prod, @RequestParam("comment") String title,
			@RequestParam("comment") String comment, @RequestParam("rating") int rating, Model model,
			@LoggedIn Optional<UserAccount> user) {
		Comment c = new Comment(title, comment, rating, LocalDateTime.now(), "");
		if (!(comment.equals("")) && user.isPresent()) {
			c.setFormatedDate(c.getDate());
			prod.addComment(c, dataService.getConcreteUserAccountRepository().findByUserAccount(user.get()).get());
			dataService.getConcreteProductRepository().save(prod);
			//catalog.save(prod);
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
	 *            the model
	 * @return the string
	 * @throws ParseException
	 *             the parse exception
	 */
	@RequestMapping(value = "/newsletter", method = RequestMethod.GET)
	public String newsletter(@RequestParam("email") String sendTo) throws ParseException {
		String text = "Sie haben sich für den Woods Super Dooper Shop Newsletter angemeldet.";
		String username;

		if (dataService.getConcreteUserAccountRepository().findByEmail(sendTo) == null) {
			username = "Nicht registierter Abonnent";
		} else {
			username = dataService.getConcreteUserAccountRepository().findByEmail(sendTo).get().getUserAccount().getUsername();
		}
		newsManager.getMap().put(username, sendTo);
		sender.sendMail(sendTo, text, "zu@googlemail.com", "NewsletterAbonnement");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/newsletter/unsubscribe", method = RequestMethod.GET)
	public String newsletterUnsubscribe(@RequestParam("email") String email) {
		if (email == "") {
			return "unsubscribe";
		}
		String username;
		if (dataService.getConcreteUserAccountRepository().findByEmail(email) != null) {
			username = dataService.getConcreteUserAccountRepository().findByEmail(email).get().getUserAccount().getUsername();
		} else {
			return "redirect:/";
		}
		if (newsManager.getMap().containsKey(username)) {
			newsManager.getMap().remove(username);
		}
		return "redirect:/";
	}
}
