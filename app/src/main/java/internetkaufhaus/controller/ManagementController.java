package internetkaufhaus.controller;

import static org.salespointframework.order.OrderStatus.OPEN;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.core.SalespointIdentifier;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.OrderIdentifier;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import internetkaufhaus.entities.Comment;
import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.entities.Newsletter;
import internetkaufhaus.entities.NewsletterIdentifier;
import internetkaufhaus.forms.EditArticleForm;
import internetkaufhaus.forms.StockForm;
import internetkaufhaus.model.NavItem;
import internetkaufhaus.model.StartPage;
import internetkaufhaus.services.ConcreteMailService;
import internetkaufhaus.services.DataService;
import internetkaufhaus.services.NewsletterService;
import internetkaufhaus.services.ProductManagementService;

// TODO: Auto-generated Javadoc
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

	/** The product management service. */
	@Autowired
	private ProductManagementService productManagementService;

	@Autowired
	private DataService dataService;

	/** The start page. */
	@Autowired
	private StartPage startPage;

	/** The Constant NONE. */
	private static final Quantity NONE = Quantity.of(0);

	/** The news manager. */
	private final NewsletterService newsManager;

	/** The sender. */
	private final ConcreteMailService sender;
	// private final List<ConcreteProduct> carousselList;
	// private final List<ConcreteProduct> selectionList;

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
	public ManagementController(NewsletterService newsManager, ConcreteMailService sender) {
		this.sender = sender;
		this.newsManager = newsManager;

	}

	/**
	 * This is a Model Attribute. It Models Attributes. Or does it Attribute
	 * Models? This function adds the Navigation Options to the navigation menu.
	 *
	 * @return the list
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
	 *            the user account
	 * @param model
	 *            the model
	 * @return the string
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
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/employee/changecatalog")
	public String articleManagement(ModelMap model) {
		Sort sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE));
		model.addAttribute("prod50", dataService.getConcreteProductRepository().findAll(sorting));
		model.addAttribute("inventory", dataService.getInventory());

		return "changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the addArticle-form for employees.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/employee/changecatalog/addArticle")
	public String addArticle(ModelMap model) {
		model.addAttribute("categories", dataService.getConcreteProductRepository().getCategories());
		return "changecatalognewitem";
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
	 * @return the string
	 */
	@RequestMapping(value = "/employee/changecatalog/addedArticle", method = RequestMethod.POST)
	public String addedArticle(@RequestParam("image") MultipartFile img,
			@ModelAttribute("editArticleForm") @Valid EditArticleForm editForm, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("message", result.getAllErrors());
			return "changecatalognewitem";
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

		productManagementService.addProduct(editForm, img);
		return "redirect:/employee/changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the edit article form for employees.
	 *
	 * @param prod
	 *            the article to edit
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/employee/changecatalog/editArticle/{prodId}")
	public String editArticle(@PathVariable("prodId") ConcreteProduct prod, ModelMap model) {
		model.addAttribute("categories", dataService.getConcreteProductRepository().getCategories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("price", prod.getPrice().getNumber());
		model.addAttribute("buyingPrice", prod.getBuyingPrice().getNumber());
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
	 * @return the string
	 */
	@RequestMapping(value = "/employee/changecatalog/editedArticle", method = RequestMethod.POST)
	public String editedArticle(@RequestParam("image") MultipartFile img,
			@ModelAttribute("editArticleForm") @Valid EditArticleForm editForm, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("concreteproduct", editForm.getProdId());
			model.addAttribute("message", result.getAllErrors());
			return "changecatalogchangeitem";
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

		productManagementService.editProduct(editForm, img);

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
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/employee/changecatalog/deleteArticle/{prodId}")
	public String deleteArticle(@PathVariable("prodId") ConcreteProduct prod, ModelMap model) {
		model.addAttribute("categories", dataService.getConcreteProductRepository().getCategories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("price", prod.getPrice().getNumber());
		return "changecatalogdeleteitem";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page deletes an article given by its ID.
	 *
	 * @param prod
	 *            the prod
	 * @return the string
	 */
	@RequestMapping(value = "/employee/changecatalog/deletedArticle/{prodId}", method = RequestMethod.GET)
	public String deletedArticle(@PathVariable("prodId") ProductIdentifier prod) {
		productManagementService.deleteProduct(prod);
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
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/employee/changecatalog/orderArticle/{prodId}")
	public String orderArticle(@PathVariable("prodId") ConcreteProduct prod, ModelMap model) {
		Optional<InventoryItem> item = dataService.getInventory().findByProductIdentifier(prod.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
		model.addAttribute("categories", dataService.getConcreteProductRepository().getCategories());
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
	 * @param userAccount
	 *            the user account
	 * @return the string
	 */
	@RequestMapping(value = "/employee/changecatalog/orderedArticle", method = RequestMethod.GET)
	public String orderedArticle(@ModelAttribute("StockForm") @Valid StockForm stockForm, BindingResult result,
			@LoggedIn Optional<UserAccount> userAccount) {
		if (result.hasErrors()) {
			return "redirect:/employee/changecatalog";
		}
		productManagementService.orderProduct(stockForm, userAccount);
		return "redirect:/employee/changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page removes an article from stock.
	 *
	 * @param stockForm
	 *            the stock form
	 * @param result
	 *            the result
	 * @return the string
	 */
	@RequestMapping(value = "/employee/changecatalog/decreasedArticle/{prodId}", method = RequestMethod.POST)
	public String decreasedArticle(@ModelAttribute("StockForm") @Valid StockForm stockForm, BindingResult result,
			@LoggedIn Optional<UserAccount> userAccount) {
		if (result.hasErrors()) {
			return "redirect:/employee/changecatalog";
		}
		productManagementService.destroyProduct(stockForm, userAccount);
		return "redirect:/employee/changecatalog";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows not (yet) accepted comments, so employees can review and
	 * reject or accept them.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/employee/comments")
	public String comments(ModelMap model) {
		List<Comment> comlist = new ArrayList<Comment>();
		for (ConcreteProduct prods : dataService.getConcreteProductRepository().findAll()) {
			comlist.addAll(prods.getUnacceptedComments());
		}
		model.addAttribute("Comments", comlist);
		return "comments";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page accepts a comment as given by its ID.
	 *
	 * @param comId
	 *            the comment to accept
	 * @return the string
	 */
	@RequestMapping("/employee/acceptComment/{comId}")
	public String acceptComments(@PathVariable("comId") long comId) {
		for (ConcreteProduct prod : dataService.getConcreteProductRepository().findAll()) {
			for (Comment c : prod.getUnacceptedComments()) {
				if (c.getId() == comId) {
					c.accept();
					c.getProduct().updateAverageRating();
					dataService.getConcreteProductRepository().save(prod);
				}
			}
		}
		return "redirect:/employee/comments";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page rejects a comment as given by its ID.
	 *
	 * @param comId
	 *            the comment to reject.
	 * @return the string
	 */
	@RequestMapping("/employee/deleteComment/{comId}")
	public String deleteComments(@PathVariable("comId") long comId) {
		boolean break_outer = false;
		for (ConcreteProduct prod : dataService.getConcreteProductRepository().findAll()) {
			if (break_outer)
				break;
			for (Comment c : prod.getComments()) {
				if (c.getId() == comId) {
					prod.removeComment(c);
					prod.updateAverageRating();
					break_outer = true;
					dataService.getConcreteProductRepository().save(prod);
					break;
				}
			}
		}

		return "redirect:/employee/comments";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows an overview of all orders.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/employee/orders")
	public String orders(ModelMap model) {
		model.addAttribute("ordersPaid", dataService.getConcreteOrderRepository().findByStatus(OrderStatus.PAID));
		model.addAttribute("ordersCancelled",
				dataService.getConcreteOrderRepository().findByStatus(OrderStatus.CANCELLED));
		model.addAttribute("ordersCompleted",
				dataService.getConcreteOrderRepository().findByStatus(OrderStatus.COMPLETED));
		return "orders";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page marks an order as accepted, sends out the corresponding E-Mail
	 * to the customer and then redirects the employee back to the order
	 * management page.
	 *
	 * @param orderId
	 *            the order id
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/employee/orders/accept/{orderId}", method = RequestMethod.GET)
	public String acceptOrder(@PathVariable("orderId") OrderIdentifier orderId, ModelMap model) {

		ConcreteOrder o = dataService.getConcreteOrderRepository().findOne(orderId);
		if (o == null) {
			model.addAttribute("msg", "error in acceptOrder, no Order with qualifier" + orderId + "found");
			return "index";
		}

		o.setStatus(OrderStatus.COMPLETED);
		dataService.getConcreteOrderRepository().save(o);

		for (OrderLine orderLine : o.getOrderLines()) {
			ConcreteProduct prod = dataService.getConcreteProductRepository().findOne(orderLine.getProductIdentifier());
			prod.increaseSold(orderLine.getQuantity().getAmount().intValue());
			dataService.getConcreteProductRepository().save(prod);
		}

		String mail = "Sehr geehrte(r) " + o.getUserAccount().getFirstname() + " " + o.getUserAccount().getLastname()
				+ "!\n";
		mail += "Ihre unten aufgeführte Bestellung vom " + o.getDateCreated().toString()
				+ " wurde von einem unserer Mitarbeiter bearbeitet und ist nun auf dem Weg zu Ihnen!\n";
		mail += "Es handelt sich um Ihre Bestellung folgender Artikel:";
		Iterator<OrderLine> i = o.getOrderLines().iterator();
		OrderLine current;
		while (i.hasNext()) {
			current = i.next();
			mail += "\n" + current.getQuantity().toString() + "x " + current.getProductName() + " für gesamt "
					+ current.getPrice().toString();
		}
		mail += "\nGesamtpreis: " + o.getTotalPrice().toString();

		sender.sendMail(o.getUserAccount().getEmail(), mail, "nobody@nothing.com", "Bestellung bearbeitet!");

		return "redirect:/employee/orders";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page cancels an order and then redirects the employee using the page
	 * back to the order overview.
	 *
	 * @param model
	 *            the model
	 * @param orderId
	 *            the order id
	 * @return the string
	 */
	@RequestMapping(value = "/employee/orders/cancel/{orderId}", method = RequestMethod.GET)
	public String cancelOrder(ModelMap model, @PathVariable("orderId") OrderIdentifier orderId) {
		ConcreteOrder o = dataService.getConcreteOrderRepository().findOne(orderId);
		if (o == null) {
			model.addAttribute("msg", "error in acceptOrder, no Order with qualifier" + orderId + "found");
			return "index";
		}
		o.setStatus(OrderStatus.CANCELLED);
		dataService.getConcreteOrderRepository().save(o);
		return "redirect:/employee/orders";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows details to a given order.
	 *
	 * @param orderId
	 *            the order id
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/employee/orders/detail/{orderId}")
	public String detailOrder(@PathVariable("orderId") OrderIdentifier orderId, ModelMap model) {
		ConcreteOrder o = dataService.getConcreteOrderRepository().findOne(orderId);
		if (o == null) {
			model.addAttribute("msg", "error in acceptOrder, no Order with qualifier" + orderId + "found");
			return "index";
		}

		if (dataService.getConcreteOrderRepository().findOne(orderId).getStatus() == OPEN) {
			Map<OrderLine, Double> orderLines = new HashMap<OrderLine, Double>();
			for (OrderLine i : o.getOrderLines()) {
				orderLines.put(i, dataService.getConcreteProductRepository().findOne(i.getProductIdentifier())
						.getBuyingPrice().multiply(i.getQuantity().getAmount()).getNumberStripped().doubleValue());
			}
			model.addAttribute("orderLines", orderLines);
			model.addAttribute("totalPrice", this.productManagementService
					.getBuyingPrice(dataService.getConcreteOrderRepository().findOne(orderId)));
		} else {
			Map<OrderLine, Double> orderLines = new HashMap<OrderLine, Double>();
			for (OrderLine i : o.getOrderLines()) {
				orderLines.put(i, i.getPrice().getNumberStripped().doubleValue());
			}
			model.addAttribute("orderLines", orderLines);
			model.addAttribute("totalPrice", dataService.getConcreteOrderRepository().findOne(orderId).getTotalPrice());
		}
		model.addAttribute("order", o);
		return "orderdetail";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the menu to edit the start page.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/employee/startpage")
	public String editStartPage(ModelMap model) {
		Map<ConcreteProduct, Boolean> bannerProducts = new HashMap<ConcreteProduct, Boolean>();
		for (ConcreteProduct i : dataService.getConcreteProductRepository().findAll()) {
			bannerProducts.put(i,
					this.startPage.getBannerProducts() != null && this.startPage.getBannerProducts().contains(i));
		}
		model.addAttribute("bannerProducts", bannerProducts);
		Map<ConcreteProduct, Boolean> selectionProducts = new HashMap<ConcreteProduct, Boolean>();
		for (ConcreteProduct i : dataService.getConcreteProductRepository().findAll()) {
			selectionProducts.put(i,
					this.startPage.getSelectionProducts() != null && this.startPage.getSelectionProducts().contains(i));
		}
		model.addAttribute("selectionProducts", selectionProducts);
		return "changestartpage";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page is called when the start page editing form is filled.
	 *
	 * @param bannerArticles
	 *            the banner articles
	 * @param selectionArticles
	 *            the selection articles
	 * @return the string
	 */
	@RequestMapping(value = "/employee/startpage/changedStartpage", method = RequestMethod.POST)
	public String changeStartpage(@RequestParam("bannerArticles") List<ProductIdentifier> bannerArticles,
			@RequestParam("selectionArticles") List<ProductIdentifier> selectionArticles) {
		List<ConcreteProduct> bannerProducts = new ArrayList<ConcreteProduct>();
		for (ProductIdentifier i : bannerArticles) {
			bannerProducts.add(dataService.getConcreteProductRepository().findOne(i));
		}
		this.startPage.setBannerProducts(bannerProducts);

		List<ConcreteProduct> selectionProducts = new ArrayList<ConcreteProduct>();
		for (ProductIdentifier i : selectionArticles) {
			selectionProducts.add(dataService.getConcreteProductRepository().findOne(i));
		}
		this.startPage.setSelectionProducts(selectionProducts);
		return "redirect:/employee/startpage";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the users, which have subscribed to the newsletter. .
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/employee/newsletter")
	public String newsletter(ModelMap model) {
		Sort sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated", Sort.NullHandling.NATIVE));
		model.addAttribute("newsletters", dataService.getNewsletterRepository().findAll(sorting));
		return "newsletter";
	}
	
	@RequestMapping(value = "/employee/newsletter/newsletter/send")
	public String sendNewsletter(@RequestParam("newsletter") NewsletterIdentifier newsId)
			throws MessagingException, IOException {
		Newsletter newsletter = dataService.getNewsletterRepository().findOne(newsId);
		newsManager.sendNewsletter(newsletter, "Martin Bens","martin.bens@live.de");
		return "redirect:/employee/newsletter";
	}
	
	@RequestMapping(value = "/employee/newsletter/newsletter/view")
	public String viewNewsletter(@RequestParam("newsletter") NewsletterIdentifier newsId, ModelMap model) {
		Newsletter newsletter = dataService.getNewsletterRepository().findOne(newsId);
		String htmlPreviewContent = newsletter.getHtmlPreviewContent();
		model.addAttribute("html", htmlPreviewContent);
		return "newsletterview";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the form to change the newsletter.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/employee/newsletter/newNewsletter")
	public String newNewsletter(ModelMap model) {
		Sort sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "name", Sort.NullHandling.NATIVE));
		model.addAttribute("prods", dataService.getConcreteProductRepository().findAll(sorting));
		model.addAttribute("htmlContent", "");
		return "newnewsletter";
	}

	@RequestMapping(value = "/employee/newsletter/newNewsletter/preview", method = RequestMethod.POST)
	public String newNewsletterPrevie(@RequestParam("prods") ProductIdentifier[] prodsArray, final Locale locale,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<ProductIdentifier> prodIds = Arrays.asList(prodsArray);
		List<ConcreteProduct> prods = dataService.getConcreteProductRepository().findByIds(prodIds);
		
		if (prodIds.size() % 3 != 0 || prodIds.size() == 0) {
			Sort sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "name", Sort.NullHandling.NATIVE));
			model.addAttribute("prods", dataService.getConcreteProductRepository().findAll(sorting));
			model.addAttribute("error", "Geben Sie mehr als 0 eine durch 3 teilbare Anzahl an Produkten an.");
			return "newnewsletter";
		}
		
		String htmlContent = newsManager.processTemplate(prods, "mail/newsletter-template.html", locale, request, response);

		htmlContent = htmlContent.replace("cid:Logo", "/resources/Bilder/Logo.png");
		htmlContent = htmlContent.replace("cid:Newsletter", "/resources/Bilder/Newsletter.png");
		htmlContent = htmlContent.replace("cid:SocialMedia", "/resources/Bilder/SocialMedia.png");
		
		htmlContent = newsManager.getPreviewHTML(htmlContent, prods);
		
		model.addAttribute("prods", prods);
		model.addAttribute("htmlContent", htmlContent);

		return "newnewsletter";

	}

	@RequestMapping(value = "/employee/newsletter/newNewsletter/created", method = RequestMethod.POST)
	public String newNewsletterCreated(@RequestParam("prods") ProductIdentifier[] prodsArray, final Locale locale,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException, MessagingException {

		List<ProductIdentifier> prodIds = Arrays.asList(prodsArray);
		
		if (prodIds.size() % 3 != 0 || prodIds.size() == 0) {
			Sort sorting = new Sort(new Sort.Order(Sort.Direction.DESC, "name", Sort.NullHandling.NATIVE));
			model.addAttribute("prods", dataService.getConcreteProductRepository().findAll(sorting));
			model.addAttribute("error", "Geben Sie mehr als 0 eine durch 3 teilbare Anzahl an Produkten an.");
			return "newnewsletter";
		}
		
		List<ConcreteProduct> prods = dataService.getConcreteProductRepository().findByIds(prodIds);
		
		String htmlContent = newsManager.processTemplate(prods, "mail/newsletter-template.html", locale, request, response);
		String htmlPreviewContent = newsManager.getPreviewHTML(htmlContent, prods);

		Newsletter newsletter = new Newsletter("mail/newsletter-template.html", htmlContent, htmlPreviewContent, prods, LocalDate.now());
		NewsletterIdentifier newsletterId = newsletter.getId();
		dataService.getNewsletterRepository().save(newsletter);
		
		System.out.println(dataService.getNewsletterRepository().findOne(newsletterId).getProductSelection());

		// newsManager.sendNewsletter(newsletter, "Martin Bens",
		// "martin.bens@live.de", locale, request, response);

		return "redirect:/employee/newsletter";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page deletes an E-Mail from the newsletter.
	 *
	 * @param mail
	 *            the mail
	 * @param name
	 *            the name
	 * @return the string
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
	 * This page shows the newsletter to the employee editing it.
	 *
	 * @param subject
	 *            the subject
	 * @param mailBody
	 *            the mail body
	 * @return the string
	 */
	@RequestMapping(value = "/employee/newsletter/changeNewsletter/showNewsletter", method = RequestMethod.GET)
	public @ResponseBody String showNewsletter(@RequestParam("subject") String subject,
			@RequestParam("mailBody") String mailBody, ModelMap model) {
		if (!(mailBody.equals(""))) {
			while (mailBody.contains("[[")) {
				int startIndex = mailBody.indexOf("[[");
				int endIndex = mailBody.indexOf("]]", startIndex);
				String productID = mailBody.substring(startIndex + 2, endIndex);
				if (productID == "top5rated") {
					mailBody = mailBody.substring(0, startIndex) + "<div class=\"product\"><h2>"
							+ "Hier sollten die Top 5 bestbewerteten Produkte stehen." + "</h2></div>"
							+ mailBody.substring(endIndex + 2);
				} else if (productID == "top5bought") {
					mailBody = mailBody.substring(0, startIndex) + "<div class=\"product\"><h2>"
							+ "Hier sollten die Top 5 meistgekauften Produkte stehen." + "</h2></div>"
							+ mailBody.substring(endIndex + 2);
				}
				ConcreteProduct product = this.dataService.getConcreteProductRepository()
						.findOne((ProductIdentifier) new SalespointIdentifier(productID));
				mailBody = mailBody.substring(0, startIndex) + "<div class=\"product\"><h2>" + product.getName()
						+ "</h2><p>" + product.getDescription() + "</p></div>" + mailBody.substring(endIndex + 2);
			}
			model.addAttribute("content", mailBody);
		}
		return "emailtemplate";
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page sends out the newsletter.
	 *
	 * @param subject
	 *            the subject
	 * @param mailBody
	 *            the mail body
	 * @return the string
	 */
	/*
	 * @RequestMapping(value =
	 * "/employee/newsletter/changeNewsletter/sendNewsletter", method =
	 * RequestMethod.GET) public String sendNewsletter(@RequestParam("subject")
	 * String subject, @RequestParam("mailBody") String mailBody) { if
	 * (!(mailBody.equals(""))) { this.newsManager.sendNewsletter(subject,
	 * mailBody); Map<String, Map<Date, String>> oldAbos =
	 * newsManager.getOldAbos(); if (oldAbos.get(subject) != null) {
	 * oldAbos.get(subject).put(new Date(), mailBody); } else { Map<Date,
	 * String> maildetails = new HashMap<Date, String>(); maildetails.put(new
	 * Date(), mailBody); oldAbos.put(subject, maildetails); } } return
	 * "redirect:/employee/newsletter"; }
	 */

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * TODO: Javadoc
	 *
	 * @param model
	 *            the model
	 * @return the string
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
	 *            the date
	 * @param subject
	 *            the subject
	 * @param model
	 *            the model
	 * @return the string
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
	 *            the model
	 * @return the retour list
	 */
	@RequestMapping(value = "/employee/returnedOrders")
	public String returnedOrders(ModelMap model) {
		model.addAttribute("ordersReturned", dataService.getConcreteOrderRepository().findByReturnedTrue());
		return "returnedOrders";
	}

}
