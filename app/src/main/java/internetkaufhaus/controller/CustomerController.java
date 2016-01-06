package internetkaufhaus.controller;

import static org.salespointframework.order.OrderStatus.OPEN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderStatus;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.forms.EditUserForm2;
import internetkaufhaus.forms.NewUserAccountForm;
import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.model.NavItem;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;
import internetkaufhaus.services.ProductManagementService;

// TODO: Auto-generated Javadoc
/**
 * This is the customer controller. It controls the customer. Or does it
 * customize the controller? You never know... In this class you may find the
 * controllers for the profile page and similar stuff, should you choose to look
 * for it.
 * 
 * @author max
 *
 */
@Controller
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
@SessionAttributes("cart")
public class CustomerController {
	
	/** The product management service. */
	@Autowired
	private ProductManagementService productManagementService;

	/** The creditmanager. */
	private final Creditmanager creditmanager;
	
	/** The user repo. */
	private final ConcreteUserAccountRepository userRepo;
	
	/** The order repo. */
	private final ConcreteOrderRepository orderRepo;

	/** The product repo. */
	private final ConcreteProductRepository concreteProductRepository;
	
	/** The form. */
	private final NewUserAccountForm form;
	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
	 *
	 * @param creditmanager            singleton, passed by spring/salespoint
	 * @param form            singleton, passed by spring/salespoint
	 
	 * @param userRepo the user repo
	 * @param concreteProductRepository the concrete product repository
	 * @param orderRepo the order repo
	 */
	@Autowired
	public CustomerController(Creditmanager creditmanager, NewUserAccountForm form, ConcreteProductRepository concreteProductRepository, ConcreteUserAccountRepository userRepo, ConcreteOrderRepository orderRepo) {
		this.creditmanager = creditmanager;
		this.orderRepo=orderRepo;
		this.userRepo = userRepo;
		this.concreteProductRepository= concreteProductRepository;
		this.form=form;
	}

	/**
	 * Adds the customer navigation.
	 *
	 * @return the list
	 */
	@ModelAttribute("customerNaviagtion")
	public List<NavItem> addAdminNavigation() {
		
		String customerNavigationName[] = {"Meine Übersicht", "Profil", "Bestellungen" };
		
		String customerNavigationLink[] = {"/customer", "/customer/profil", "/customer/orders" };
		List<NavItem> navigation = new ArrayList<NavItem>();
		for (int i = 0; i < customerNavigationName.length; i++) {
			NavItem nav = new NavItem(customerNavigationName[i], customerNavigationLink[i], "non-category");
			navigation.add(nav);
		}
		return navigation;
	}
	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the account details of a given account.
	 *
	 * @param userAccount            the account to show details about
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/customer")
	public String customer(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		creditmanager.updateCreditpointsByUser(userRepo.findByUserAccount(userAccount.get()));
		model.addAttribute("account", userAccount.get());
		model.addAttribute("points", userRepo.findByUserAccount(userAccount.get()).getCredits());
		model.addAttribute("ordercount", ((Collection<?>)orderRepo.findByUser(userAccount.get())).size());
		model.addAttribute("recruits",userRepo.findByUserAccount(userAccount.get()).getRecruits());
		return "customer";
	}
	
	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param userAccount the userAccount
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/customer/orders")
	public String customerOrders(@LoggedIn Optional<UserAccount> userAccount, ModelMap model) {
		List<ConcreteOrder> paid = new ArrayList<ConcreteOrder>();
		List<ConcreteOrder>	retourned = new ArrayList<ConcreteOrder>();
		List<ConcreteOrder> completed = new ArrayList<ConcreteOrder>();
		Iterable<ConcreteOrder> orders = orderRepo.findByUser(userAccount.get());
		for( ConcreteOrder o : orders){
		
			if(o.getOrder().getOrderStatus()==OrderStatus.COMPLETED){
				completed.add(o);
			}
			if(o.getOrder().getOrderStatus()==OrderStatus.PAID){
				paid.add(o);
			}
			if (o.getReturned() == true) {
				retourned.add(o);
			}
		}
		
		model.addAttribute("ordersPaid", paid);
		model.addAttribute("ordersRetourned", retourned);
		model.addAttribute("ordersCompleted", completed);
		return "ordersofUser";
	}
	
	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param orderId the orderId
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/customer/orders/detail/{orderId}")
	public String detailOrder(@PathVariable("orderId") Long orderId, ModelMap model) {
		ConcreteOrder o = orderRepo.findById(orderId);
		if (o == null) {
			model.addAttribute("msg", "error in acceptOrder, no Order with qualifier" + orderId + "found");
			return "index";
		}

		if (orderRepo.findById(orderId).getStatus() == OPEN) {
			Map<OrderLine, Double> orderLines = new HashMap<OrderLine, Double>();
			for (OrderLine i : o.getOrder().getOrderLines()) {
				orderLines.put(i, this.concreteProductRepository.findByProductIdentifier(i.getProductIdentifier())
						.getBuyingPrice().multiply(i.getQuantity().getAmount()).getNumberStripped().doubleValue());
			}
			model.addAttribute("orderLines", orderLines);
			model.addAttribute("totalPrice",
					this.productManagementService.getBuyingPrice(orderRepo.findById(orderId)));
		} else {
			Map<OrderLine, Double> orderLines = new HashMap<OrderLine, Double>();
			for (OrderLine i : o.getOrder().getOrderLines()) {
				orderLines.put(i, i.getPrice().getNumberStripped().doubleValue());
			}
			model.addAttribute("orderLines", orderLines);
			model.addAttribute("totalPrice", orderRepo.findById(orderId).getOrder().getTotalPrice());
		}
		model.addAttribute("order", o);
		return "orderdetail";
	}
	
	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param userAccount the userAccount
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/customer/profil")
	public String profil(@LoggedIn Optional<UserAccount> userAccount, ModelMap model){
		model.addAttribute("account", userRepo.findByUserAccount(userAccount.get()));
		
		return "profil";
	}
	
	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param acc the acc
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "/customer/editUser/{id}")
	public String editUser(@PathVariable("id") ConcreteUserAccount acc, ModelMap model) {
		model.addAttribute("account", acc);
		return "edituser";
	}
	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 *
	 * @param edituserform the edituserform
	 * @param result the result
	 * @return the string
	 */
	@RequestMapping(value = "/customer/editedUser", method = RequestMethod.POST)
	public String editedUserUser(@ModelAttribute("EditUserForm2") @Valid EditUserForm2 edituserform,
			BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/customer/profil/";
		}
		form.changeUser2(edituserform.getFirstname(),edituserform.getLastname(),edituserform.getAddress(),edituserform.getCity(),edituserform.getZipCode(), edituserform.getEmail(),edituserform.getId(), edituserform.getPassword());
		return "redirect:/customer/profil/";
	}
}