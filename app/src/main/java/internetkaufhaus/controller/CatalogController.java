package internetkaufhaus.controller;

import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.data.domain.Pageable;
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


	@Autowired
	public CatalogController(Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, Search prodSearch, ConcreteProductRepository concreteCatalog, NewsletterManager newsManager, MailSender sender,ConcreteUserAccountRepository usermanager) {

		this.catalog = catalog;
		this.inventory = inventory;
		this.prodSearch = prodSearch;
		this.concreteCatalog = concreteCatalog;
		this.newsManager= newsManager;
		this.sender=sender;
		this.usermanager=usermanager;
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
	
	@RequestMapping(path = "/catalog/{type}/{representation}/{split}/{pagenumber}", method = { RequestMethod.POST, RequestMethod.GET })
	public String list50(Pageable pagable, @RequestParam(value = "total", defaultValue = "0") Integer total, @PathVariable("type") String category, @PathVariable("split") int split, @PathVariable("pagenumber") int number, @PathVariable("representation") int representation, ModelMap model) {
		if (split == 0)
			split = 3;
		if (total != 0)
			split = total;
		Page<ConcreteProduct> page = concreteCatalog.findByCategory(category, new PageRequest(number-1,split));
		List<Integer> numbers = IntStream.range(1, page.getTotalPages()+1).boxed().collect(Collectors.toList());
		if(number == 0)
			number = 1;
		if(number > numbers.size())
			number = numbers.size();
		model.addAttribute("category", category);
		model.addAttribute("number", number);
		Set<Integer> quantities = Sets.newSet(split, 2, 5, 10, 20, 50, 100, 200, prodSearch.getProdsByCategory(category).size());
		quantities.removeIf(i -> i > prodSearch.getProdsByCategory(category).size());
		model.addAttribute("maximum", prodSearch.getProdsByCategory(category).size());
		model.addAttribute("quantities", new TreeSet<Integer>(quantities));
		model.addAttribute("representation",representation);
		model.addAttribute("split", split);
		model.addAttribute("prods", page = concreteCatalog.findByCategory(category, new PageRequest(number-1,split)));
		model.addAttribute("numbers", IntStream.range(1, page.getTotalPages()+1).boxed().collect(Collectors.toList()));
		return "catalog";
	}

	@RequestMapping(value = "/catalog/{type}/{split}/{pagenumber}/changedSetting", method = RequestMethod.POST)
	public String changeStartPageSetting(Pageable pagable, @PathVariable("type") String category, @PathVariable("pagenumber") int number, @RequestParam("total") int split, ModelMap model) {
		return "redirect:/catalog/"+category+'/'+split+'/'+number;
		model.addAttribute("prods", page);
		model.addAttribute("numbers", numbers);
		model.addAttribute("sites", numbers.size());
		model.addAttribute("categories", prodSearch.getCategories());
		return "catalog";
	}

	@RequestMapping(value = "/catalog/{type}/{representation}/{split}/{pagenumber}/changedSetting", method = RequestMethod.POST)
	public String changeStartPageSetting(Pageable pagable, @PathVariable("type") String category, @PathVariable("pagenumber") int number, @RequestParam("total") int split, @PathVariable("representation") int representation, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCategories());
		return "redirect:/catalog/"+category+'/'+representation+'/'+split+'/'+number;
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
			prod.addComment(c,usermanager.findByUserAccount(user.get()));
			catalog.save(prod);
			model.addAttribute("time", c.getFormatedDate());
		}
		return "redirect:detail/" + prod.getIdentifier();
	}

	@RequestMapping(value="/newsletter", method=RequestMethod.GET)
	public String newsletter(@RequestParam("email") String sendTo, ModelMap model) throws ParseException{
		ConcreteMailSender concreteMailSender = new ConcreteMailSender(sender);
		String text="Sie haben sich für den Woods Super Dooper Shop Newsletter angemeldet.";
		String username;
		
		if(usermanager.findByEmail(sendTo)==null){
			username="Nicht registierter Abonnet";
		}
		else username=usermanager.findByEmail(sendTo).getUserAccount().getUsername();
		
		newsManager.getMap().put(username, sendTo);
		concreteMailSender.sendMail(sendTo, text,"zu@googlemail.com", "NewsletterAbonnement");
	
		model.addAttribute("prodList", catalog.findAll());
		
		return "index";
		
		
			
		}
}
