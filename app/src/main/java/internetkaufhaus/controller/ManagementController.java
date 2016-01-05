package internetkaufhaus.controller;

import static org.salespointframework.core.Currencies.EURO;
import static org.salespointframework.order.OrderStatus.OPEN;

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
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import internetkaufhaus.entities.Comment;
import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.forms.EditArticleForm;
import internetkaufhaus.forms.StockForm;
import internetkaufhaus.model.NavItem;
import internetkaufhaus.model.Search;
import internetkaufhaus.model.StartPage;
import internetkaufhaus.model.StockManager;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.NewsletterService;
import internetkaufhaus.services.ProductManagementService;

/**
 * This is the management controller. It controls the management. Or does it
 * manage the controls? You never know... In this class you may find the
 * controllers for the employee interfaces, should you choose to look for them.
 * 
 * @author max
 *
 */
@Controller
@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
@SessionAttributes("cart")
public class ManagementController {

	@Autowired
	private ProductManagementService productManagementService;

	@Autowired
	private StartPage startPage;

	private static final Quantity NONE = Quantity.of(0);
	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
	private final Search prodSearch;
	private final ConcreteOrderRepository concreteOrderRepo;
	private final ConcreteProductRepository concreteProductRepository;
	private final OrderManager<Order> orderManager;
	private final StockManager stock;
	private final NewsletterService newsManager;
	private final ConcreteMailService sender;
	// private final List<ConcreteProduct> carousselList;
	// private final List<ConcreteProduct> selectionList;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
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
	public ManagementController(ConcreteProductRepository concreteProductRepository, OrderManager<Order> orderManager,
			ConcreteOrderRepository concreteOrderRepo, Catalog<ConcreteProduct> catalog,
			Inventory<InventoryItem> inventory, Search prodSearch, StockManager stock, NewsletterService newsManager,
			ConcreteMailService sender) {
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

	/**
	 * This is a Model Attribute. It Models Attributes. Or does it Attribute
	 * Models? This function adds the Navigation Options to the navigation menu.
	 * 
	 * @return
	 */
	@ModelAttribute("employeeNaviagtion")
	public List<NavItem> addEmployeeNavigation() {
		String employeeNavigationName[] = { "Katalog/Lager", "Bestellungen", "Bewertungen", "Retouren", "Newsletter",
				"Startseite" };
		String employeeNavigationLink[] = { "/employee/changecatalog", "/employee/orders", "/employee/comments",
				"/employee/returnedOrders", "/employee/newsletter", "/employee/startpage" };
		List<NavItem> navigation = new ArrayList<NavItem>();
		for (int i = 0; i < employeeNavigationName.length; i++) {
			NavItem nav = new NavItem(employeeNavigationName[i], employeeNavigationLink[i], "non-category");
			navigation.add(nav);
		}
		return navigation;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the main menu for the administration interface of
	 * employees.
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
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows all current products in the catalog, ascending by name.
	 * 
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
	 * This page shows the addArticle-form for employees.
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
	 * This page accepts a comment as given by its ID.
	 * 
	 * @param comId
	 *            the comment to accept
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
	 * This page rejects a comment as given by its ID.
	 * 
	 * @param comId
	 *            the comment to reject.
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
					prods.updateAverageRating();
					break_outer = true;
					// prevents modification during interaction
					break;
				}
			}
			this.catalog.save(prods);
		}

		return "redirect:/employee/comments";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows not (yet) accepted comments, so employees can review and
	 * reject or accept them.
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
		return "comments";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the edit article form for employees.
	 * 
	 * @param prod
	 *            the article to edit
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/changecatalog/editArticle/{prodId}")
	public String editArticle(@PathVariable("prodId") ConcreteProduct prod, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCategories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("price", prod.getPrice().getNumber());
		model.addAttribute("buyingPrice", prod.getBuyingPrice());
		return "changecatalogchangeitem";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page edits an article as requested by an employee and then redirects
	 * the user to the article Overview.
	 * 
	 * @param editForm
	 *            the form in which the employee specified which article to edit
	 * @param img
	 *            the new image which the should have.
	 * @param result
	 *            the result which (in)validates above mentioned form
	 * @return
	 */
	@RequestMapping(value = "/employee/changecatalog/editedArticle", method = RequestMethod.POST)
	public String editedArticle(@ModelAttribute("editArticleForm") @Valid EditArticleForm editForm,
			@RequestParam("image") MultipartFile img, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/employee/changecatalog/editArticle/";
		}

		if (!img.isEmpty()) {
			try {
				byte[] bytes = img.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(img.getOriginalFilename())));
				// TODO: generate filename
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				System.out.println("Error while uploading image file: " + e.getMessage());
			}
		} else {
			System.out.println("no file submitted, nothing to see here.");
		}

		ConcreteProduct prod = editForm.getProdId();

		prod.setCategory(editForm.getCategory());
		prod.addCategory(editForm.getCategory());
		prod.setName(editForm.getName());
		prod.setPrice(Money.of(editForm.getPrice(), EURO));
		prod.setBuyingPrice(Money.of(editForm.getBuyingPrice(), EURO));
		prod.setDescription(editForm.getDescription());

		if (!(img.getOriginalFilename().isEmpty())) {
			prod.setImagefile(img.getOriginalFilename());
		}

		catalog.save(prod);
		concreteProductRepository.save(prod);

		return "redirect:/employee/changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page adds an article as requested by an employee and then redirects
	 * the user to the article Overview.
	 * 
	 * @param editForm
	 *            the form in which the employee specified which article to add
	 * @param img
	 *            the new image which the should have.
	 * @param result
	 *            the result which (in)validates above mentioned form
	 * @return
	 */
	@RequestMapping(value = "/employee/changecatalog/addedArticle", method = RequestMethod.POST)
	public String addedArticle(@ModelAttribute("editArticleForm") @Valid EditArticleForm editForm,
			@RequestParam("image") MultipartFile img, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/employee/changecatalog/addArticle/";
		}

		if (!img.isEmpty()) {
			try {
				byte[] bytes = img.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("filename"));
				// TODO: generate filename
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				System.out.println("error (" + e.getMessage() + ") !!!");
			}
		} else {
			System.out.println("another error (file empty) !!!");
		}

		ConcreteProduct prodId = new ConcreteProduct(editForm.getName(), Money.of(editForm.getPrice(), EURO),
				Money.of(editForm.getBuyingPrice(), EURO), editForm.getCategory(), editForm.getDescription(), "",
				img.getOriginalFilename());
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
	 * This page shows the delete article form which the user has to fill to
	 * confirm the deletion of an article.
	 * 
	 * @param prod
	 *            the product ID of the article to remove
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
	 * This page deletes an article given by its ID.
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
	 * This page shows the order-article-form where employees choose how many
	 * items of an article they want to order.
	 * 
	 * @param prod
	 *            the ID of the article to order
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
		model.addAttribute("buyingPrice", prod.getBuyingPrice());
		return "changecatalogorderitem";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page orders a given amount of articles and then redirects to the
	 * article management overview.
	 * 
	 * @param stockForm
	 *            the form which contains the article and the amount to order
	 * @param result
	 *            the result which (in)validates the form
	 * @param model
	 * @param userAccount
	 * @return
	 */
	@RequestMapping(value = "/employee/changecatalog/orderedArticle", method = RequestMethod.GET)
	public String orderedArticle(@ModelAttribute("StockForm") @Valid StockForm stockForm, BindingResult result,
			@LoggedIn Optional<UserAccount> userAccount) {
		if (result.hasErrors()) {
			return "redirect:/employee/changecatalog";
		}
		ConcreteOrder order = new ConcreteOrder(userAccount.get(), Cash.CASH);
		OrderLine orderLine = new OrderLine(catalog.findOne(stockForm.getProdId()).get(),
				Quantity.of(stockForm.getQuantity()));
		order.getOrder().add(orderLine);
		order.setDateOrdered(LocalDateTime.now());
		orderManager.save(order.getOrder());
		concreteOrderRepo.save(order);
		stock.orderArticle(stockForm.getProdId(), Quantity.of(stockForm.getQuantity()));
		return "redirect:/employee/changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page removes an article from stock.
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
	 * This page shows the menu to edit the start page.
	 * 
	 * @param totalCaroussel
	 * @param totalSelection
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/startpage")
	public String editStartPage(ModelMap model) {
		Map<ConcreteProduct, Boolean> bannerProducts = new HashMap<ConcreteProduct, Boolean>();
		for (ConcreteProduct i : catalog.findAll()) {
			bannerProducts.put(i,
					this.startPage.getBannerProducts() != null && this.startPage.getBannerProducts().contains(i));
		}
		model.addAttribute("bannerProducts", bannerProducts);
		Map<ConcreteProduct, Boolean> selectionProducts = new HashMap<ConcreteProduct, Boolean>();
		for (ConcreteProduct i : catalog.findAll()) {
			selectionProducts.put(i,
					this.startPage.getSelectionProducts() != null && this.startPage.getSelectionProducts().contains(i));
		}
		model.addAttribute("selectionProducts", selectionProducts);
		System.out.println(bannerProducts);
		System.out.println(selectionProducts);
		return "changestartpage";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page is called when the start page editing form is filled.
	 * 
	 * @param changeStartPageForm
	 * @return
	 */
	@RequestMapping(value = "/employee/startpage/changedStartpage", method = RequestMethod.POST)
	public String changeStartpage(@RequestParam("bannerArticles") List<ProductIdentifier> bannerArticles,
			@RequestParam("selectionArticles") List<ProductIdentifier> selectionArticles) {
		System.out.println(bannerArticles);
		System.out.println(selectionArticles);
		List<ConcreteProduct> bannerProducts = new ArrayList<ConcreteProduct>();
		for (ProductIdentifier i : bannerArticles) {
			bannerProducts.add(this.concreteProductRepository.findByProductIdentifier(i));
		}
		this.startPage.setBannerProducts(bannerProducts);

		List<ConcreteProduct> selectionProducts = new ArrayList<ConcreteProduct>();
		for (ProductIdentifier i : selectionArticles) {
			selectionProducts.add(this.concreteProductRepository.findByProductIdentifier(i));
		}
		this.startPage.setSelectionProducts(selectionProducts);
		System.out.println(bannerProducts);
		System.out.println(selectionProducts);
		return "redirect:/employee/startpage";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows an overview of all orders.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/employee/orders")
	public String orders(ModelMap model) {
		Iterable<ConcreteOrder> ordersPaid = concreteOrderRepo.findByStatus(OrderStatus.PAID);
		Iterable<ConcreteOrder> ordersCancelled = concreteOrderRepo.findByStatus(OrderStatus.CANCELLED);
		Iterable<ConcreteOrder> ordersCompleted = concreteOrderRepo.findByStatus(OrderStatus.COMPLETED);
		// System.out.println("Paid Orders:" + ordersPaid + "Cancelled Orders:"
		// + ordersCancelled + "Completed Orders:" + ordersCompleted);

		model.addAttribute("ordersPaid", ordersPaid);
		model.addAttribute("ordersCancelled", ordersCancelled);
		model.addAttribute("ordersCompleted", ordersCompleted);
		return "orders";

	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page marks an order as accepted, sends out the corresponding E-Mail
	 * to the customer and then redirects the employee back to the order
	 * management page.
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
			prod.increaseSold(orderLine.getQuantity().getAmount().intValue());

			catalog.save(prod);
			concreteProductRepository.save(prod);
		}

		String mail = "Sehr geehrte(r) " + order.getUserAccount().getFirstname() + " "
				+ order.getUserAccount().getLastname() + "!\n";
		mail += "Ihre unten aufgeführte Bestellung vom " + order.getDateCreated().toString()
				+ " wurde von einem unserer Mitarbeiter bearbeitet und ist nun auf dem Weg zu Ihnen!\n";
		mail += "Es handelt sich um Ihre Bestellung folgender Artikel:";
		Iterator<OrderLine> i = order.getOrderLines().iterator();
		OrderLine current;
		while (i.hasNext()) {
			current = i.next();
			mail += "\n" + current.getQuantity().toString() + "x " + current.getProductName() + " für gesamt "
					+ current.getPrice().toString();
		}
		mail += "\nGesamtpreis: " + order.getTotalPrice().toString();

		sender.sendMail(order.getUserAccount().getEmail(), mail, "nobody@nothing.com", "Bestellung bearbeitet!");

		// orderManager.completeOrder(o.getOrder());
		return "redirect:/employee/orders";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page cancels an order and then redirects the employee using the page
	 * back to the order overview.
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
	 * This page shows details to a given order.
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

		if (concreteOrderRepo.findById(orderId).getStatus() == OPEN) {
			Map<OrderLine, Double> orderLines = new HashMap<OrderLine, Double>();
			for (OrderLine i : o.getOrder().getOrderLines()) {
				orderLines.put(i, this.concreteProductRepository.findByProductIdentifier(i.getProductIdentifier())
						.getBuyingPrice().multiply(i.getQuantity().getAmount()).getNumberStripped().doubleValue());
			}
			model.addAttribute("orderLines", orderLines);
			model.addAttribute("totalPrice",
					this.productManagementService.getBuyingPrice(concreteOrderRepo.findById(orderId)));
		} else {
			Map<OrderLine, Double> orderLines = new HashMap<OrderLine, Double>();
			for (OrderLine i : o.getOrder().getOrderLines()) {
				orderLines.put(i, i.getPrice().getNumberStripped().doubleValue());
			}
			model.addAttribute("orderLines", orderLines);
			model.addAttribute("totalPrice", concreteOrderRepo.findById(orderId).getOrder().getTotalPrice());
		}
		model.addAttribute("order", o);
		return "orderdetail";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the users, which have subscribed to the newsletter.
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
	 * This page shows the form to change the newsletter.
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
	 * This page deletes an E-Mail from the newsletter.
	 * 
	 * @param mail
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/employee/newsletter/deleteUserAbo/{mail}/{username}")
	public String deleteUserAbo(@PathVariable("mail") String mail, @PathVariable("username") String name) {
		if (newsManager.getMap().get(name) == mail) {
			newsManager.getMap().remove(name);
		}
		return "redirect:/employee/newsletter";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page sends out the newsletter.
	 * 
	 * @param subject
	 * @param mailBody
	 * @return
	 */
	@RequestMapping(value = "/employee/newsletter/changeNewsletter/sendNewsletter", method = RequestMethod.GET)
	public String sendNewsletter(@RequestParam("subject") String subject, @RequestParam("mailBody") String mailBody) {
		Map<Date, String> maildetails = new HashMap<Date, String>();
		if (!(mailBody.equals(""))) {
			for (String mail : this.newsManager.getMap().values()) {
				sender.sendMail(mail, mailBody, "zu@googlemail.com", subject);
			}
			maildetails.put(new Date(), mailBody);
			newsManager.getOldAbos().put(subject, maildetails);
		}
		return "redirect:/employee/newsletter";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * TODO: Javadoc
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
	 * TODO: Javadoc
	 * 
	 * @param date
	 * @param subject
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/employee/newsletter/oldAbos/{date}/{subject}")
	public String oldAbosdetails(@PathVariable("date") String date, @PathVariable("subject") String subject,
			ModelMap model) {
		model.addAttribute("date", date);
		model.addAttribute("mailsubject", subject);
		model.addAttribute("mailtext", newsManager.getOldAbos().get(subject));

		return "oldnewsletterdetail";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows a list of returned orders.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/employee/returnedOrders")
	public String getRetourList(ModelMap model) {
		List<ConcreteOrder> retourList = new ArrayList<ConcreteOrder>();
		for (ConcreteOrder o : concreteOrderRepo.findAll()) {
			if (o.getReturned() == true) {
				retourList.add(o);
			}
		}
		model.addAttribute("retourList", retourList);
		return "returnedOrders";
	}

	/**
	 * TODO: What does this do?
	 * 
	 * @return
	 */
	public Inventory<InventoryItem> getInventory() {
		return inventory;
	}

	/**
	 * TODO: What does this do?
	 * 
	 * @return
	 */
	public static Quantity getNone() {
		return NONE;
	}

}
