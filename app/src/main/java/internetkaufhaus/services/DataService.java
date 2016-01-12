package internetkaufhaus.services;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.repositories.ConcreteInventory;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;
import internetkaufhaus.repositories.NewsletterRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class DataService.
 */
@Service
public class DataService {

	/** The catalog. */
	private final Catalog<ConcreteProduct> catalog;

	/** The inventory. */
	private final Inventory<InventoryItem> inventory;

	private final ConcreteInventory<InventoryItem> concreteInventory;

	/** The concrete product repo. */
	private final ConcreteProductRepository concreteProductRepository;

	/** The concrete account repo. */
	private final ConcreteUserAccountRepository concreteUserAccountRepository;

	/** The user account manager. */
	private final UserAccountManager userAccountManager;

	/** The concrete order repo. */
	private final ConcreteOrderRepository concreteOrderRepository;

	/** The order manager. */
	private final OrderManager<Order> orderManager;

	private final NewsletterRepository newsletterRepository;

	/**
	 * Instantiates a new data service.
	 *
	 * @param catalog
	 *            the catalog
	 * @param inventory
	 *            the inventory
	 * @param concreteProductRepo
	 *            the concrete product repo
	 * @param concreteAccountRepo
	 *            the concrete account repo
	 * @param userAccountManager
	 *            the user account manager
	 * @param concreteOrderRepo
	 *            the concrete order repo
	 * @param orderManager
	 *            the order manager
	 */
	@Autowired
	public DataService(Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory,
			ConcreteInventory<InventoryItem> concreteInventory, ConcreteProductRepository concreteProductRepositroy,
			ConcreteUserAccountRepository concreteAccountRepository, UserAccountManager userAccountManager,
			ConcreteOrderRepository concreteOrderRepository, OrderManager<Order> orderManager,
			NewsletterRepository newsletterRepository) {
		this.catalog = catalog;
		this.inventory = inventory;
		this.concreteInventory = concreteInventory;
		this.concreteProductRepository = concreteProductRepositroy;
		this.concreteUserAccountRepository = concreteAccountRepository;
		this.userAccountManager = userAccountManager;
		this.concreteOrderRepository = concreteOrderRepository;
		this.orderManager = orderManager;
		this.newsletterRepository = newsletterRepository;
	}

	/**
	 * Gets the order manager.
	 *
	 * @return the order manager
	 */
	public OrderManager<Order> getOrderManager() {
		return this.orderManager;
	}

	/**
	 * Gets the catalog.
	 *
	 * @return the catalog
	 */
	public Catalog<ConcreteProduct> getCatalog() {
		return this.catalog;
	}

	/**
	 * Gets the inventory.
	 *
	 * @return the inventory
	 */
	public Inventory<InventoryItem> getInventory() {
		return this.inventory;
	}

	public ConcreteInventory<InventoryItem> getConcreteInventory() {
		return this.concreteInventory;
	}

	public ConcreteOrderRepository getConcreteOrderRepository() {
		return concreteOrderRepository;
	}

	/**
	 * Gets the user account manager.
	 *
	 * @return the user account manager
	 */
	public UserAccountManager getUserAccountManager() {
		return this.userAccountManager;
	}

	public ConcreteProductRepository getConcreteProductRepository() {
		return concreteProductRepository;
	}


	public NewsletterRepository getNewsletterRepository() {
		return this.newsletterRepository;
	}
	
	public ConcreteUserAccountRepository getConcreteUserAccountRepository() {
		return concreteUserAccountRepository;
	}
}
