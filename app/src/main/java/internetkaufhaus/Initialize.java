package internetkaufhaus;

import static org.salespointframework.core.Currencies.EURO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.model.AccountAdministration;
import internetkaufhaus.model.Search;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@Component
public class Initialize implements DataInitializer {
	private final ConcreteUserAccountRepository ConcreteUserAccountManager;
	private final UserAccountManager userAccountManager;
	private final Inventory<InventoryItem> inventory;
	private final Catalog<ConcreteProduct> productCatalog;
	private final OrderManager<Order> orderManager;
	private final AccountAdministration accountAdministration;
	private final MailSender sender;
	private final ConcreteProductRepository concreteProductRepository;
	private final ConcreteOrderRepository concreteOrderRepo;
	private final Search productSearch;


	@Autowired
	public Initialize(ConcreteOrderRepository concreteOrderRepo,Catalog<ConcreteProduct> productCatalog, UserAccountManager userAccountManager, ConcreteUserAccountRepository ConcreteUserAccountManager, Inventory<InventoryItem> inventory, OrderManager<Order> orderManager, Search productSearch, AccountAdministration accountAdministration, MailSender sender, ConcreteProductRepository concreteProductRepository) {
		this.inventory = inventory;
		this.ConcreteUserAccountManager = ConcreteUserAccountManager;
		this.userAccountManager = userAccountManager;
		this.productCatalog = productCatalog;
		this.productSearch = productSearch;
		this.productSearch.setCatalog(productCatalog);
		this.orderManager = orderManager;
		this.concreteProductRepository = concreteProductRepository;
		this.sender = sender;
		this.accountAdministration = accountAdministration;
		this.accountAdministration.setUserAccountManager(this.userAccountManager);
		this.accountAdministration.setConcreteUserAccountManager(this.ConcreteUserAccountManager);
		this.accountAdministration.setMailSender(this.sender);
		this.concreteOrderRepo = concreteOrderRepo;
	}

	@Override
	public void initialize() {
		// fill the user database
		initializeUsers(userAccountManager, ConcreteUserAccountManager);
		// fill the Catalog with Items
		initializeCatalog(productCatalog, inventory, productSearch);
		// fill inventory with Inventory items
		// Inventory Items consist of one ConcreteProduct and a number
		// representing the stock
		initializeInventory(productCatalog, inventory);
		initializeOrders(concreteOrderRepo ,concreteProductRepository, orderManager, ConcreteUserAccountManager);

	}

	private void initializeCatalog(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory, Search productSearch) {
		// prevents the Initializer to run in case of data persistance
		if (productCatalog.count() > 0) {
			return;
		}
		ConcreteProduct p1 = new ConcreteProduct("Delikatesse 1", Money.of(0.99, EURO), "Delikatessen", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "delikatessen.png");
		ConcreteProduct p2 = new ConcreteProduct("Delikatesse 2", Money.of(0.99, EURO), "Delikatessen", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "delikatessen.png");
		ConcreteProduct p3 = new ConcreteProduct("Delikatesse 3", Money.of(0.99, EURO), "Delikatessen", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "delikatessen.png");

		ConcreteProduct p4 = new ConcreteProduct("Wein 1", Money.of(0.99, EURO), "Wein und Gourmet", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "wein.jpg");
		ConcreteProduct p5 = new ConcreteProduct("Wein 2", Money.of(0.99, EURO), "Wein und Gourmet", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "wein.jpg");
		ConcreteProduct p6 = new ConcreteProduct("Wein 3", Money.of(0.99, EURO), "Wein und Gourmet", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "wein.jpg");

		ConcreteProduct p7 = new ConcreteProduct("Zigarre 1", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");
		ConcreteProduct p8 = new ConcreteProduct("Zigarre 2", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");
		ConcreteProduct p9 = new ConcreteProduct("Zigarre 3", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");
		ConcreteProduct p10 = new ConcreteProduct("Zigarre 4", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");
		ConcreteProduct p11 = new ConcreteProduct("Zigarre 5", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz", "zagarre.jpg");

		/*
		 * Comment p= new Comment( "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren" ,4, new Date(),"");
		 * 
		 * p11.addreviewedComments(p); p3.addreviewedComments(p); p6.addreviewedComments(p); p1.addreviewedComments(p); p9.addreviewedComments(p); p5.addreviewedComments(p);
		 */

		List<ConcreteProduct> prods = new ArrayList<ConcreteProduct>(Arrays.asList( p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		productCatalog.save(prods);
		concreteProductRepository.save(prods);

		productSearch.addProds(productCatalog.findAll());
	}

	private void initializeInventory(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory) {
		// prevents the Initializer to run in case of data persistance
		for (ConcreteProduct prod : productCatalog.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(prod, Quantity.of(50));
			inventory.save(inventoryItem);
		}
	}

	private void initializeUsers(UserAccountManager userAccountManager, ConcreteUserAccountRepository ConcreteUserAccountManager) {
		// prevents the Initializer to run in case of data persistance
		if (userAccountManager.findByUsername("peon").isPresent()) {
			return;
		}

		final Role adminRole = Role.of("ROLE_ADMIN");
		final Role customerRole = Role.of("ROLE_CUSTOMER");
		final Role employeeRole = Role.of("ROLE_EMPLOYEE");

		List<ConcreteUserAccount> userAccounts = new ArrayList<ConcreteUserAccount>();
		userAccounts.add(new ConcreteUserAccount("peon", "peon", adminRole, userAccountManager));
		userAccounts.add(new ConcreteUserAccount("saul", "saul", employeeRole, userAccountManager));
		userAccounts.add(new ConcreteUserAccount("adminBehrens@todesstern.ru", "admin", "admin", "Behrens", "Musterstraße", "01069", "Definitiv nicht Dresden", "admin", customerRole, userAccountManager));
		userAccounts.add(new ConcreteUserAccount("behrens_lars@gmx.de", "lars", "Lars", "Behrens", "Musterstraße", "01069", "Definitiv nicht Dresden", "lars", customerRole, userAccountManager));
		
//		100 weitere ConcreteUserAccounts; auskommentiert aus Zeitgründen
//		
//		int i = 0;
//		for(i=0;i<100;i++)
//		{
//			userAccounts.add(new ConcreteUserAccount("kunde"+i+"@todesstern.ru", "kunde"+i, "Kunde"+i, "Kundenname"+i, "Kundenstraße"+i, "12345", "Definitiv nicht Dresden", "kunde"+i, customerRole, userAccountManager,"lars", i));
//		}
		
	
		
		/*
		 * RegistrationForm reg = new RegistrationForm(); reg.setEmail("behrens_lars@gmx.de"); reg.setName("peons"); reg.setPassword("asdf"); reg.setPasswordrepeat("asdf");
		 * 
		 */

		for (ConcreteUserAccount acc : userAccounts) {
			userAccountManager.save(acc.getUserAccount());
			ConcreteUserAccountManager.save(acc);
		}
		ConcreteUserAccountManager.findByUserAccount(userAccountManager.findByUsername("lars").get()).setRecruitedBy("adminBehrens@todesstern.ru");
		ConcreteUserAccountManager.findByUserAccount(userAccountManager.findByUsername("admin").get()).setRecruitedBy("behrens_lars@gmx.de");
		// System.out.println("###############"+ConcreteUserAccountManager.findByEmail("behrens_lars@gmx.de").toString());
	}

	// public OrderManager<Order> getOrderManager() {
	// return orderManager;
	//
	// }

	private void initializeOrders(ConcreteOrderRepository concreteOrderRepo, ConcreteProductRepository prods, OrderManager<Order> orderManager, ConcreteUserAccountRepository ConcreteUserAccountManager) {
		
		Cart c = new Cart();
		for (ConcreteProduct p : prods.findAll()) {
			c.addOrUpdateItem(p, Quantity.of(1));
		}
		for (ConcreteUserAccount u : ConcreteUserAccountManager.findByRole(Role.of("ROLE_CUSTOMER"))) {
		

   			ConcreteOrder order = new ConcreteOrder(u.getUserAccount(), Cash.CASH);
			c.addItemsTo(order.getOrder());

            Order o = order.getOrder();
			c.addItemsTo(o);

			order.setBillingGender("Herr");
			order.setBillingFirstName(u.getUserAccount().getFirstname());
			order.setBillingLastName(u.getUserAccount().getLastname());
			order.setBillingStreet(u.getAddress());
			order.setBillingHouseNumber("2");
			order.setBillingTown(u.getCity());
			order.setBillingZipCode(u.getZipCode());
			
			order.setShippingGender("Herr");
			order.setShippingFirstName(u.getUserAccount().getFirstname());
			order.setShippingLastName(u.getUserAccount().getLastname());
			order.setShippingStreet(u.getAddress());
			order.setShippingHouseNumber("2");
			order.setShippingTown(u.getCity());
			order.setShippingZipCode(u.getZipCode());
			
			orderManager.payOrder(o);// only set orderManager.payOrder(o), do not use orderManager.completeOrder(0), to complete Order look at the next line!
			order.setStatus(OrderStatus.COMPLETED); //to complete Order do not use orderManager.completeOrder
            order.setDateOrdered(LocalDateTime.now().minusDays(31));
        	concreteOrderRepo.save(order);
    		orderManager.save(o);
           


		}

		c.clear();

	}
}
