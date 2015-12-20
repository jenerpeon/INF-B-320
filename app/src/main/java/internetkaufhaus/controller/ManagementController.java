package internetkaufhaus.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.collections.IteratorUtils;
import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.Interval;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import internetkaufhaus.entities.Comment;
import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.forms.EditArticleForm;
import internetkaufhaus.forms.StockForm;
import internetkaufhaus.model.ConcreteMailSender;
import internetkaufhaus.model.NavItem;
import internetkaufhaus.model.NewsletterManager;
import internetkaufhaus.model.Search;
import internetkaufhaus.model.Statistic;
import internetkaufhaus.model.StockManager;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteProductRepository;

/**
 * This is the management controller. It controls the management. Or does it manage the controls? You never know...
 * In this class you may find the controllers for the employee interfaces, should you choose to look for them.
 * 
 * @author max
 *
 */
@Controller
@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
public class ManagementController {

	private static final Quantity NONE = Quantity.of(0);
	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
	private final Search prodSearch;
	private final ConcreteOrderRepository concreteOrderRepo;
	private final ConcreteProductRepository concreteProductRepository;
	private final OrderManager<Order> orderManager;
	private final StockManager stock;
	private final NewsletterManager newsManager;
	private final MailSender sender;
	// private final List<ConcreteProduct> carousselList;
	// private final List<ConcreteProduct> selectionList;

	/**
	 * This is the constructor. It's neither used nor does it contain any functionality other than storing function arguments as class attribute, what do you expect me to write here?
	 * 
	 * @param concreteProductRepository
	 * @param orderManager
	 * @param concreteOrderRepo
	 * @param catalog
	 * @param inventory
	 * @param prodSearch
	 * @param stock
	 * @param newsManager
	 * @param sender
	 */
	@Autowired
	public ManagementController(ConcreteProductRepository concreteProductRepository, OrderManager<Order> orderManager, ConcreteOrderRepository concreteOrderRepo, Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, Search prodSearch, StockManager stock, NewsletterManager newsManager, MailSender sender) {
		this.concreteProductRepository = concreteProductRepository;
		this.catalog = catalog;
		this.inventory = inventory;
		this.prodSearch = prodSearch;
		this.concreteOrderRepo = concreteOrderRepo;
		this.stock = stock;
		this.sender = sender;
		this.newsManager = newsManager;
		this.orderManager = orderManager;
		// this.carousselList = carousselList;
		// this.selectionList = selectionList;
	}
	
	@ModelAttribute("employeeNaviagtion")
	public List<NavItem> addEmployeeNavigation() {
		String employeeNavigationName[] = {"Katalog/Lager","Bestellungen","Bewertungen","Retouren","Newsletter","Startseite"};
		String employeeNavigationLink[] = {"/employee/changecatalog","/employee/orders","/employee/comments","/employee/returnedOrders","/employee/newsletter","/employee/startpage/3/8"};
		List<NavItem> navigation = new ArrayList<NavItem>();
		for (int i=0; i < employeeNavigationName.length; i++) {
			NavItem nav = new NavItem(employeeNavigationName[i],employeeNavigationLink[i],"non-category");
			navigation.add(nav);
		}
		System.out.println("Hier werden keine Kategorien abgerufen");
		return navigation;
	}
	
	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param userAccount
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee")
	public String employeeStart(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("account", userAccount.get());
		return "employee";
	}

	/**
	 * gives The catalog in ascending order by its Product name.
	 * 
	 * @param userAccount
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/changecatalog")
	public String articleManagement(ModelMap model) {
		model.addAttribute("prod50", concreteProductRepository.findAllByOrderByName());
		model.addAttribute("inventory", inventory);

		return "changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/changecatalog/addArticle")
	public String addArticle(ModelMap model) {
		model.addAttribute("categories", prodSearch.getCategories());
		return "changecatalognewitem";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param comId
	 * @return
	 */
	@RequestMapping("/employee/acceptComment/{comId}")
	public String acceptComments(@PathVariable("comId") long comId) {
		for (ConcreteProduct prods : catalog.findAll()) {
			for (Comment c : prods.getUnacceptedComments()) {
				if (c.getId() == comId) {
					c.accept();
					c.getProduct().updateAverageRating();
				}
			}
			this.catalog.save(prods);
		}
		return "redirect:/employee/comments";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param comId
	 * @return
	 */
	@RequestMapping("/employee/deleteComment/{comId}")
	public String deleteComments(@PathVariable("comId") long comId) {
		boolean break_outer = false;
		for (ConcreteProduct prods : catalog.findAll()) {
			if (break_outer)
				break;
			for (Comment c : prods.getComments()) {
				if (c.getId() == comId) {
					prods.removeComment(c);
					c.getProduct().updateAverageRating();
					break_outer = true;
					// prevents modification while interation
					break;
				}
			}
			this.catalog.save(prods);
		}

		return "redirect:/employee/comments";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/comments")
	public String comments(ModelMap model) {
		List<Comment> comlist = new ArrayList<Comment>();
		for (ConcreteProduct prods : catalog.findAll()) {
			comlist.addAll(prods.getUnacceptedComments());
		}

		model.addAttribute("Comments", comlist);
		// model.addAttribute("concretProduct", com.getKey(com.values));

		return "comments";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param prod
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/changecatalog/editArticle/{prodId}")
	public String editArticle(@PathVariable("prodId") ConcreteProduct prod, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCategories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("price", prod.getPrice().getNumber());
		return "changecatalogchangeitem";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param editForm
	 * @param img
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/employee/changecatalog/editedArticle", method = RequestMethod.POST)
	public String editedArticle(@ModelAttribute("editArticleForm") @Valid EditArticleForm editForm, @RequestParam("image") MultipartFile img, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/employee/changecatalog/editArticle/";
		}

		if (!img.isEmpty()) {
			try {
				byte[] bytes = img.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("filename"))); // TODO: generate filename
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				System.out.println("error (" + e.getMessage() + ") !!!");
			}
		} else {
			System.out.println("another error (file empty) !!!");
		}

		ConcreteProduct prodId = editForm.getProdId();
		prodId.addCategory(editForm.getCategory());
		prodId.setName(editForm.getName());
		prodId.setPrice(Money.of(editForm.getPrice(), "EUR"));
		prodId.setDescription(editForm.getDescription());

		if (!(img.getOriginalFilename().isEmpty())) {
			prodId.setImagefile(img.getOriginalFilename());
		}

		catalog.save(prodId);
		concreteProductRepository.save(prodId);

		return "redirect:/employee/changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param editForm
	 * @param img
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/employee/changecatalog/addedArticle", method = RequestMethod.POST)
	public String addedArticle(@ModelAttribute("editArticleForm") @Valid EditArticleForm editForm, @RequestParam("image") MultipartFile img, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/employee/changecatalog/addArticle/";
		}

		if (!img.isEmpty()) {
			try {
				byte[] bytes = img.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("filename"))); // TODO: generate filename
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				System.out.println("error (" + e.getMessage() + ") !!!");
			}
		} else {
			System.out.println("another error (file empty) !!!");
		}

		ConcreteProduct prodId = new ConcreteProduct(editForm.getName(), Money.of(editForm.getPrice(), "EUR"), editForm.getCategory(), editForm.getDescription(), "", img.getOriginalFilename());

		catalog.save(prodId);

		List<ConcreteProduct> prods = new ArrayList<ConcreteProduct>();
		prods.add(prodId); // TODO: das hier ist offensichtlich.
		prodSearch.addProds(prods);

		InventoryItem inventoryItem = new InventoryItem(prodId, Quantity.of(0));
		inventory.save(inventoryItem);

		return "redirect:/employee/changecatalog";

	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param prod
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/changecatalog/deleteArticle/{prodId}")
	public String deleteArticle(@PathVariable("prodId") ConcreteProduct prod, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCategories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("price", prod.getPrice().getNumber());
		return "changecatalogdeleteitem";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param prod
	 * @return
	 */
	@RequestMapping(value = "/employee/changecatalog/deletedArticle/{prodId}", method = RequestMethod.GET)
	public String deletedArticle(@PathVariable("prodId") ProductIdentifier prod) {

		prodSearch.delete(catalog.findOne(prod).get());
		inventory.delete(inventory.findByProductIdentifier(prod).get());
		catalog.delete(catalog.findOne(prod).get());

		return "redirect:/employee/changecatalog";

	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param prod
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/changecatalog/orderArticle/{prodId}")
	public String orderArticle(@PathVariable("prodId") ConcreteProduct prod, ModelMap model) {

		Optional<InventoryItem> item = inventory.findByProductIdentifier(prod.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
		model.addAttribute("categories", prodSearch.getCategories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("quantity", quantity);
		model.addAttribute("price", prod.getPrice().getNumber());
		return "changecatalogorderitem";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param stockForm
	 * @param result
	 * @param model
	 * @param userAccount
	 * @return
	 */
	@RequestMapping(value = "/employee/changecatalog/orderedArticle", method = RequestMethod.GET)
	public String orderedArticle(@ModelAttribute("StockForm") @Valid StockForm stockForm, BindingResult result, @LoggedIn Optional<UserAccount> userAccount) {
		if (result.hasErrors()) {
			return "redirect:/employee/changecatalog";
		}
		ConcreteOrder order = new ConcreteOrder(userAccount.get(), Cash.CASH);

		OrderLine orderLine = new OrderLine(catalog.findOne(stockForm.getProdId()).get(), Quantity.of(stockForm.getQuantity()));

		order.getOrder().add(orderLine);

		order.setDateOrdered(LocalDateTime.now());
		orderManager.save(order.getOrder());
		concreteOrderRepo.save(order);

		stock.orderArticle(stockForm.getProdId(), Quantity.of(stockForm.getQuantity()));
		return "redirect:/employee/changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param stockForm
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/employee/changecatalog/decreasedArticle/{prodId}", method = RequestMethod.POST)
	public String decreasedArticle(@ModelAttribute("StockForm") @Valid StockForm stockForm, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/employee/changecatalog";
		}
		stock.removeArticle(stockForm.getProdId(), Quantity.of(stockForm.getQuantity()));
		return "redirect:/employee/changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param totalCaroussel
	 * @param totalSelection
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/startpage/{totalCaroussel}/{totalSelection}")
	public String editStartPage(@PathVariable("totalCaroussel") int totalCaroussel, @PathVariable("totalSelection") int totalSelection, ModelMap model) {
		model.addAttribute("prod50", catalog.findAll());
		model.addAttribute("totCar", totalCaroussel);
		model.addAttribute("totSel", totalSelection);
		return "changestartpage";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param totalCaroussel
	 * @param totalSelection
	 * @return
	 */
	@RequestMapping(value = "/employee/startpage/changedSetting", method = RequestMethod.POST)
	public String changeStartPageSetting(@RequestParam("totalCaroussel") int totalCaroussel, @RequestParam("totalSelection") int totalSelection) {
		return "redirect:/employee/startpage/" + totalCaroussel + '/' + totalSelection;
	}
	/*
	 * @RequestMapping(value = "/employee/startpage/changedstartpage", method = RequestMethod.POST) public String changeStartpage(@ModelAttribute ChangeStartPageForm changeStartPageForm) { List<ProductIdentifier> carousselProdsId = changeStartPageForm.getCarousselArticle(); List<ProductIdentifier> selectionProdsId = changeStartPageForm.getSelectionArticle(); int index = 0; for (ProductIdentifier prodId : carousselProdsId) { carousselList.set(index, catalog.findOne(prodId).get()); index ++; } index = 0; for (ProductIdentifier prodId : selectionProdsId) { selectionList.set(index, catalog.findOne(prodId).get()); index ++; } return "redirect:/"; }
	 */

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/employee/orders")
	public String orders(ModelMap model) {
		Iterable<ConcreteOrder> ordersPaid = concreteOrderRepo.findByStatus(OrderStatus.PAID);
		Iterable<ConcreteOrder> ordersCancelled = concreteOrderRepo.findByStatus(OrderStatus.CANCELLED);
		Iterable<ConcreteOrder> ordersCompleted = concreteOrderRepo.findByStatus(OrderStatus.COMPLETED);
		// System.out.println("Paid Orders:" + ordersPaid + "Cancelled Orders:" + ordersCancelled + "Completed Orders:" + ordersCompleted);

		model.addAttribute("ordersPaid", ordersPaid);
		model.addAttribute("ordersCancelled", ordersCancelled);
		model.addAttribute("ordersCompleted", ordersCompleted);
		return "orders";

	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/employee/orders/accept/{orderId}", method = RequestMethod.GET)
	public String acceptOrder(@PathVariable("orderId") Long orderId, ModelMap model) {

		ConcreteOrder o = concreteOrderRepo.findById(orderId);
		if (o == null) {
			model.addAttribute("msg", "error in acceptOrder, no Order with qualifier" + orderId + "found");
			return "index";
		}

		Order order = o.getOrder();
		o.setStatus(OrderStatus.COMPLETED);
		concreteOrderRepo.save(o);

		Iterable<OrderLine> orders = order.getOrderLines();
		Collection<OrderLine> orderLines = IteratorUtils.toList(orders.iterator());
		for (OrderLine orderLine : orderLines) {
			ConcreteProduct prod = catalog.findOne(orderLine.getProductIdentifier()).get();
			prod.increaseSelled(orderLine.getQuantity().getAmount().intValue());

			catalog.save(prod);
			concreteProductRepository.save(prod);
		}

		String mail = "Sehr geehrte(r) " + order.getUserAccount().getFirstname() + " " + order.getUserAccount().getLastname() + "!\n";
		mail += "Ihre unten aufgeführte Bestellung vom " + order.getDateCreated().toString() + " wurde von einem unserer Mitarbeiter bearbeitet und ist nun auf dem Weg zu Ihnen!\n";
		mail += "Es handelt sich um Ihre Bestellung folgender Artikel:";
		Iterator<OrderLine> i = order.getOrderLines().iterator();
		OrderLine current;
		while (i.hasNext()) {
			current = i.next();
			mail += "\n" + current.getQuantity().toString() + "x " + current.getProductName() + " für gesamt " + current.getPrice().toString();
		}
		mail += "\nGesamtpreis: " + order.getTotalPrice().toString();

		new ConcreteMailSender(this.sender).sendMail(order.getUserAccount().getEmail(), mail, "nobody@nothing.com", "Bestellung bearbeitet!");

		// orderManager.completeOrder(o.getOrder());
		return "redirect:/employee/orders";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/employee/orders/cancel/{orderId}", method = RequestMethod.GET)
	public String cancelOrder(ModelMap model, @PathVariable("orderId") Long orderId) {
		ConcreteOrder o = concreteOrderRepo.findById(orderId);
		if (o == null) {
			model.addAttribute("msg", "error in acceptOrder, no Order with qualifier" + orderId + "found");
			return "index";
		}
		orderManager.cancelOrder(o.getOrder());
		return "redirect:/employee/orders";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/employee/orders/detail/{orderId}")
	public String detailOrder(@PathVariable("orderId") Long orderId, ModelMap model) {
		ConcreteOrder o = concreteOrderRepo.findById(orderId);
		if (o == null) {
			model.addAttribute("msg", "error in acceptOrder, no Order with qualifier" + orderId + "found");
			return "index";
		}

		Iterable<OrderLine> orderLines = o.getOrder().getOrderLines();
		model.addAttribute("order", o);
		model.addAttribute("orderLines", orderLines);
		return "orderdetail";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/employee/newsletter")
	public String newsletter(ModelMap model) {
		model.addAttribute("newsUser", newsManager.getMap());
		return "newsletter";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param userAccount
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/newsletter/changeNewsletter")
	public String changeNewsletter(ModelMap model) {
		model.addAttribute("categories", prodSearch.getCategories());
		return "changenewsletter";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param mail
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/employee/newsletter/deleteUserAbo/{mail}/{username}")
	public String deleteUserAbo(@PathVariable("mail") String mail, @PathVariable("username") String name) {
		newsManager.getMap().remove(name); // TODO: why do I need the mail here?
		return "redirect:/employee/newsletter";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param subject
	 * @param mailBody
	 * @return
	 */
	@RequestMapping(value = "/employee/newsletter/changeNewsletter/sendNewsletter", method = RequestMethod.GET)
	public String sendNewsletter(@RequestParam("subject") String subject, @RequestParam("mailBody") String mailBody) {
		Map<Date, String> maildetails = new HashMap<Date, String>();
		ConcreteMailSender concreteMailSender = new ConcreteMailSender(sender);
		if (!(mailBody.equals(""))) {
			for (String mail : this.newsManager.getMap().values()) {
				concreteMailSender.sendMail(mail, mailBody, "zu@googlemail.com", subject);
			}
			maildetails.put(new Date(), mailBody);
			newsManager.getOldAbos().put(subject, maildetails);
		}
		return "redirect:/employee/newsletter";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/employee/newsletter/oldAbos")
	public String oldAbos(ModelMap model) {

		model.addAttribute("mailComponents", newsManager.getOldAbos());

		return "oldnewsletterstable";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param date
	 * @param subject
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/employee/newsletter/oldAbos/{date}/{subject}")
	public String oldAbosdetails(@PathVariable("date") String date, @PathVariable("subject") String subject, ModelMap model) {
		model.addAttribute("date", date);
		model.addAttribute("mailsubject", subject);
		model.addAttribute("mailtext", newsManager.getOldAbos().get(subject));

		return "oldnewsletterdetail";
	}

	public Inventory<InventoryItem> getInventory() {
		return inventory;
	}

	public static Quantity getNone() {
		return NONE;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * 
	 * @param model
	 * @param f_year
	 * @param f_month
	 * @param f_day
	 * @param t_year
	 * @param t_month
	 * @param t_day
	 * @param quantize
	 * @return
	 */
	@RequestMapping(value = "/admin/statistic")
	public String getStatistic(ModelMap model, @RequestParam(value = "f_year") int f_year, @RequestParam(value = "f_month") int f_month, @RequestParam(value = "f_day") int f_day, @RequestParam(value = "t_year") int t_year, @RequestParam(value = "t_month") int t_month, @RequestParam(value = "t_day") int t_day, @RequestParam(value = "quantize") int quantize) {
		Statistic stat = new Statistic(orderManager);
		LocalDateTime f = LocalDateTime.of(f_year, f_month, f_day, 0, 0);
		LocalDateTime t = LocalDateTime.of(t_year, t_month, t_day, 0, 0);
		Interval i = Interval.from(f).to(t);
		model.addAttribute("turnover", stat.getTurnoverByInterval(i, quantize));
		model.addAttribute("sales", stat.getSalesByInterval(i, quantize));
		model.addAttribute("purchases", null);
		model.addAttribute("profit", null);
		return "statistics";
	}
	
	@RequestMapping(value="/employee/returnedOrders")
	public String getRetourList(ModelMap model){
		List<ConcreteOrder>retourList= new ArrayList<ConcreteOrder>();
		for( ConcreteOrder o :concreteOrderRepo.findAll()){
			if(o.getReturned()==true){
				retourList.add(o);
			}
		}
		model.addAttribute("retourList", retourList);
		return "returnedOrders";
	}

}
