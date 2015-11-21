package internetkaufhaus;

import static org.salespointframework.core.Currencies.EURO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internetkaufhaus.model.AccountAdministration;
import internetkaufhaus.model.Comment;
import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.ConcreteUserAccount;
import internetkaufhaus.model.ConcreteUserAccountRepository;
import internetkaufhaus.model.search;

@Component
public class Initialize implements DataInitializer {
	private final ConcreteUserAccountRepository ConcreteUserAccountManager;
	private final UserAccountManager userAccountManager;
	private final Inventory<InventoryItem> inventory;
	private final Catalog<ConcreteProduct> productCatalog;
	private final OrderManager<Order> orderManager;
	private final AccountAdministration accountAdministration;

	private final search productSearch;

	@Autowired
	public Initialize(Catalog<ConcreteProduct> productCatalog, UserAccountManager userAccountManager,
			ConcreteUserAccountRepository ConcreteUserAccountManager, Inventory<InventoryItem> inventory,
			OrderManager<Order> orderManager, search productSearch, AccountAdministration accountAdministration) {
		this.inventory = inventory;
		this.ConcreteUserAccountManager = ConcreteUserAccountManager;
		this.userAccountManager = userAccountManager;
		this.productCatalog = productCatalog;
		this.productSearch = productSearch;
		this.orderManager = orderManager;
		this.accountAdministration = accountAdministration;
		this.accountAdministration.setUserAccountManager(this.userAccountManager);
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

	}

	private void initializeCatalog(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory,
			search productSearch) {
		// prevents the Initializer to run in case of data persistance
		if (productCatalog.count() > 0) {
			return;
		}

		ConcreteProduct p1 = new ConcreteProduct("Delikatesse 1", Money.of(0.99, EURO), "Delikatessen",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz", "delikatessen.png");
		ConcreteProduct p2 = new ConcreteProduct("Delikatesse 2", Money.of(0.99, EURO), "Delikatessen",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz","delikatessen.png");
		ConcreteProduct p3 = new ConcreteProduct("Delikatesse 3", Money.of(0.99, EURO), "Delikatessen",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz","delikatessen.png");

		ConcreteProduct p4 = new ConcreteProduct("Wein 1", Money.of(0.99, EURO), "Wein und Gourmet",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz","wein.jpg");
		ConcreteProduct p5 = new ConcreteProduct("Wein 2", Money.of(0.99, EURO), "Wein und Gourmet",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz", "wein.jpg");
		ConcreteProduct p6 = new ConcreteProduct("Wein 3", Money.of(0.99, EURO), "Wein und Gourmet",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz", "wein.jpg");

		ConcreteProduct p7 = new ConcreteProduct("Zigarre 1", Money.of(0.99, EURO), "Tabakwaren",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz","zagarre.jpg");
		ConcreteProduct p8 = new ConcreteProduct("Zigarre 2", Money.of(0.99, EURO), "Tabakwaren",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz","zagarre.jpg");
		ConcreteProduct p9 = new ConcreteProduct("Zigarre 3", Money.of(0.99, EURO), "Tabakwaren",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz","zagarre.jpg");
		ConcreteProduct p10 = new ConcreteProduct("Zigarre 4", Money.of(0.99, EURO), "Tabakwaren",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz","zagarre.jpg");
		ConcreteProduct p11 = new ConcreteProduct("Zigarre 5", Money.of(0.99, EURO), "Tabakwaren",
				"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",
				"https://eng.wikipedia.org/wiki/Fuzz","zagarre.jpg");
		
		/*Comment p= new Comment("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren",4, new Date(),"");
		
		p11.addreviewedComments(p);
		p3.addreviewedComments(p);
		p6.addreviewedComments(p);
		p1.addreviewedComments(p);
		p9.addreviewedComments(p);
		p5.addreviewedComments(p);*/
		
		productCatalog.save(p1);
		productCatalog.save(p2);
		productCatalog.save(p3);
		productCatalog.save(p4);
		productCatalog.save(p5);
		productCatalog.save(p6);
		productCatalog.save(p7);
		productCatalog.save(p8);
		productCatalog.save(p9);
		productCatalog.save(p10);
		productCatalog.save(p11);

		productSearch.addProds(productCatalog.findAll());
	}

	private void initializeInventory(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory) {
		// prevents the Initializer to run in case of data persistance
		for (ConcreteProduct prod : productCatalog.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(prod, Quantity.of(10));
			inventory.save(inventoryItem);
		}
	}

	private void initializeUsers(UserAccountManager userAccountManager,
			ConcreteUserAccountRepository ConcreteUserAccountManager) {
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
		userAccounts.add(new ConcreteUserAccount("admin", "admin", customerRole, userAccountManager));
		userAccounts.add(new ConcreteUserAccount("darth@vader.dark", "Anikan", "Skywalker", "Tatooine Outa RIM", "dark",
				adminRole, userAccountManager));

		for (ConcreteUserAccount acc : userAccounts) {
			userAccountManager.save(acc.getUserAccount());
			ConcreteUserAccountManager.save(acc);
		}
	}

	public OrderManager<Order> getOrderManager() {
		return orderManager;
	}
}
