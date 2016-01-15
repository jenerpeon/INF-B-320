package internetkaufhaus;

import java.text.NumberFormat;
import java.text.ParseException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FileUtils;
import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.svenjacobs.loremipsum.LoremIpsum;
import internetkaufhaus.entities.Comment;
import internetkaufhaus.entities.ConcreteOrder;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.entities.ConcreteUserAccount;
import internetkaufhaus.model.Creditmanager;
import internetkaufhaus.model.StartPage;
import internetkaufhaus.services.DataService;

/**
 * This class initializes default data which is used to test the functionality
 * of the whole project.
 * 
 * @author max
 *
 */
@Component
public class Initialize implements DataInitializer {

	/** The start page. */
	private final StartPage startPage;

	private final DataService dataService;
	
	private final Creditmanager creditmanager;

	// private final Map<String, String> recruits;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
	 *
	 * @param startPage
	 *            the start page
	 * @param concreteOrderRepo
	 *            singleton, passed by spring/salespoint
	 * @param productCatalog
	 *            singleton, passed by spring/salespoint
	 * @param userAccountManager
	 *            singleton, passed by spring/salespoint
	 * @param ConcreteUserAccountManager
	 *            singleton, passed by spring/salespoint
	 * @param inventory
	 *            singleton, passed by spring/salespoint
	 * @param orderManager
	 *            singleton, passed by spring/salespoint
	 * @param productSearch
	 *            singleton, passed by spring/salespoint
	 * @param concreteProductRepository
	 *            singleton, passed by spring/salespoint
	 */
	@Autowired
	public Initialize(StartPage startPage, DataService dataService, Creditmanager creditmanager) {
		this.startPage = startPage;
		this.dataService = dataService;
		this.creditmanager = creditmanager;
	}

	/**
	 * This function calls other functions that initialize certain data types.
	 */
	@Override
	public void initialize() {
		// fill the user database
		try {
			initializeUsers();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// fill the Catalog with Items
		try {
			initializeCatalog();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// fill inventory with Inventory items
		// Inventory Items consist of one ConcreteProduct and a number
		// representing the stock
		initializeInventory();
		initializeComments();
		initializeOrders();

	}

	/**
	 * This function initializes the catalog. Who would've thought!
	 *
	 * @param productCatalog
	 *            the product catalog
	 * @param productSearch
	 *            the product search
	 * @throws IOException
	 */
	private void initializeCatalog() throws IOException {

		if (dataService.getConcreteProductRepository().numberOfFindAll() > 0) {
			return;
		}

		List<ConcreteProduct> prods = new ArrayList<ConcreteProduct>();
		List<String> products = FileUtils.readLines(new File("products.txt"), "utf-8");

		for (String productString : products) {
			List<String> data = Arrays.asList(productString.split(";"));
			ConcreteProduct prod;
			try {
				prod = new ConcreteProduct(data.get(0),
						Money.of(NumberFormat.getNumberInstance(Locale.GERMAN).parse(data.get(1)), "EUR"),
						Money.of(NumberFormat.getNumberInstance(Locale.GERMAN).parse(data.get(2)), "EUR"), data.get(3),
						data.get(4), data.get(5), data.get(6));
				prods.add(prod);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		dataService.getConcreteProductRepository().save(prods);

		this.startPage.setBannerProducts(new ArrayList<ConcreteProduct>());
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			this.startPage.addBannerProduct(prods.get(random.nextInt(prods.size())));
		}

		this.startPage.setSelectionProducts(new ArrayList<ConcreteProduct>());
		for (int i = 0; i < 16; i++) {
			this.startPage.addSelectionProduct(prods.get(random.nextInt(prods.size())));
		}
	}

	/**
	 * This function initializes the inventory. Who would've thought!
	 *
	 * @param productCatalog
	 *            the product catalog
	 * @param inventory
	 *            the inventory
	 */
	private void initializeInventory() {

		for (ConcreteProduct prod : dataService.getConcreteProductRepository().findAll()) {
			InventoryItem inventoryItem = new InventoryItem(prod, Quantity.of(0));
			dataService.getConcreteInventory().save(inventoryItem);
		}
	}

	/**
	 * This function initializes the users. Who would've thought!
	 *
	 * @param userAccountManager
	 *            the user account manager
	 * @param ConcreteUserAccountManager
	 *            the concrete user account manager
	 * @throws IOException
	 */
	private void initializeUsers() throws IOException {
		// prevents the Initializer to run in case of data persistance
		if (dataService.getUserAccountManager().findByUsername("peon").isPresent()) {
			return;
		}

		final Role adminRole = Role.of("ROLE_ADMIN");
		final Role customerRole = Role.of("ROLE_CUSTOMER");
		final Role employeeRole = Role.of("ROLE_EMPLOYEE");

		List<ConcreteUserAccount> userAccounts = new ArrayList<ConcreteUserAccount>();
		userAccounts.add(new ConcreteUserAccount("peon", "peon", adminRole, dataService.getUserAccountManager()));
		userAccounts.add(new ConcreteUserAccount("saul", "saul", employeeRole, dataService.getUserAccountManager()));
		userAccounts.add(new ConcreteUserAccount("adminBehrens@todesstern.ru", "admin", "admin", "Behrens",
				"Musterstraße", "3", "01069", "Definitiv nicht Dresden", "admin", customerRole,
				dataService.getUserAccountManager()));
		userAccounts.add(new ConcreteUserAccount("behrens_lars@gmx.de", "lars", "Lars", "Behrens", "Musterstraße", "3",
				"01069", "Definitiv nicht Dresden", "lars", customerRole, dataService.getUserAccountManager()));

		List<String> accounts = FileUtils.readLines(new File("accounts.txt"), "utf-8");

		for (String accountString : accounts) {
			List<String> data = Arrays.asList(accountString.split(";"));
			ConcreteUserAccount account = new ConcreteUserAccount(data.get(0), data.get(1), data.get(2), data.get(3),
					data.get(4), data.get(5), data.get(6), data.get(7), data.get(8), customerRole,
					dataService.getUserAccountManager());
			userAccounts.add(account);
		}

		for (ConcreteUserAccount account : userAccounts) {
			Random random = new Random();
			ConcreteUserAccount recruiter = userAccounts.get(random.nextInt(userAccounts.size() - 1));
			recruiter.setRecruits(account);
			// recruits.put(account.getId().toString(),
			// recruiter.getId().toString());
		}

		for (ConcreteUserAccount acc : userAccounts) {
			dataService.getUserAccountManager().save(acc.getUserAccount());
			dataService.getConcreteUserAccountRepository().save(acc);
		}

		dataService.getConcreteUserAccountRepository()
				.findByUserAccount(dataService.getUserAccountManager().findByUsername("lars").get()).get().setRecruits(
						dataService.getConcreteUserAccountRepository().findByEmail("adminBehrens@todesstern.ru").get());
		dataService.getConcreteUserAccountRepository()
				.findByUserAccount(dataService.getUserAccountManager().findByUsername("admin").get()).get()
				.setRecruits(dataService.getConcreteUserAccountRepository().findByEmail("behrens_lars@gmx.de").get());
	}

	@SuppressWarnings("unchecked")
	private void initializeComments() {

		Collection<ConcreteProduct> prods = IteratorUtils
				.toList(dataService.getConcreteProductRepository().findAll().iterator());
		Collection<ConcreteUserAccount> accountsCollection = IteratorUtils
				.toList(dataService.getConcreteUserAccountRepository().findAll().iterator());
		List<ConcreteUserAccount> accountsList = new ArrayList<ConcreteUserAccount>(accountsCollection);

		for (ConcreteProduct prod : prods) {
			Random random = new Random();
			LoremIpsum lorem = new LoremIpsum();
			for (int i = 0; i < random.nextInt(4) + 2; i++) {
				long epochNow = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(1));
				long epochBegin = 1403215200;
				LocalDateTime commentDate = LocalDateTime.ofEpochSecond(
						epochBegin + ((long) (random.nextDouble() * (epochNow - epochBegin))), 0,
						ZoneOffset.ofHours(1));

				Comment comment = new Comment(lorem.getWords(random.nextInt(10) + 4),
						lorem.getWords(random.nextInt(100) + 10), random.nextInt(4) + 1, commentDate,
						commentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
				prod.addComment(comment, accountsList.get(random.nextInt(accountsList.size() - 1)));
			}
		}

		for (ConcreteProduct prod : prods) {
			for (Comment c : prod.getUnacceptedComments()) {
				c.accept();
				c.getProduct().updateAverageRating();
			}
		}

	}

	/**
	 * This function initializes the orders. Who would've thought!
	 *
	 * @param concreteOrderRepo
	 *            the concrete order repo
	 * @param prods
	 *            the prods
	 * @param orderManager
	 *            the order manager
	 * @param ConcreteUserAccountManager
	 *            the concrete user account manager
	 */
	@SuppressWarnings("unchecked")
	private void initializeOrders() {

		Random rand = new Random();

		Collection<ConcreteProduct> allProducts = IteratorUtils
				.toList(dataService.getConcreteProductRepository().findAll().iterator());
		List<ConcreteProduct> allProductsList = new ArrayList<ConcreteProduct>(allProducts);

		for (ConcreteUserAccount u : dataService.getConcreteUserAccountRepository()
				.findByRole(Role.of("ROLE_CUSTOMER"))) {
			int orderNumber = rand.nextInt(3) + 2;
			for (int i = 0; i < orderNumber; i++) {
				Cart orderCart = new Cart();
				Cart stockCart = new Cart();
				int productNumber = rand.nextInt(2) + 1;
				for (int j = 0; j < productNumber; j++) {
					ConcreteProduct prod = allProductsList.get(rand.nextInt(allProductsList.size() - 1));
					Quantity quant = Quantity.of(new Long(rand.nextInt(5) + 1));
					orderCart.addOrUpdateItem(prod, quant);
					while (!dataService.getConcreteInventory().findByProduct(prod).get().hasSufficientQuantity(quant)) {
						dataService.getConcreteInventory().findByProduct(prod).ifPresent(x -> {
							x.increaseQuantity(Quantity.of(20));
							dataService.getConcreteInventory().save(x);
							stockCart.addOrUpdateItem(prod, Quantity.of(20));
						});
					}
					dataService.getConcreteInventory().findByProduct(prod).ifPresent(x -> {
						x.decreaseQuantity(quant);
						dataService.getConcreteInventory().save(x);
					});
					prod.increaseSold(quant.getAmount().intValue());
					dataService.getConcreteProductRepository().save(prod);

				}

				ConcreteOrder order = new ConcreteOrder(u, Cash.CASH);
				orderCart.addItemsTo(order);

				ConcreteOrder stock = new ConcreteOrder(dataService.getConcreteUserAccountRepository()
						.findByUserAccount(dataService.getUserAccountManager().findByUsername("saul").get()).get(),
						Cash.CASH);
				stockCart.addItemsTo(stock);

				List<String> address = new ArrayList<String>();
				address.add("Herr");
				address.add(u.getUserAccount().getFirstname());
				address.add(u.getUserAccount().getLastname());
				address.add(u.getStreet());
				address.add(u.getHouseNumber());
				address.add("");
				address.add(u.getCity());
				address.add(u.getZipCode());

				order.setBillingAddress(address);
				order.setShippingAddress(address);

				long epochNow = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(1));
				long epochBegin = 1403215200;
				// long epochBegin =
				// LocalDateTime.now().minusDays(5).toEpochSecond(ZoneOffset.ofHours(1));
				Long orderDate = (long) (rand.nextDouble() * (epochNow - epochBegin) + epochBegin);
				order.setDateOrdered(orderDate);

				stock.setDateOrdered(orderDate);

				order.setStatus(OrderStatus.COMPLETED);

				dataService.getConcreteOrderRepository().save(order);

				if (!stockCart.isEmpty()) {
					dataService.getConcreteOrderRepository().save(stock);
				}

				orderCart.clear();
				stockCart.clear();
			}
		}
	}
}