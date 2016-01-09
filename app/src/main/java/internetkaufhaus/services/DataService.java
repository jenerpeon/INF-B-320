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
	private final ConcreteProductRepository concreteProductRepo;

	/** The concrete account repo. */
	private final ConcreteUserAccountRepository concreteAccountRepo;

	/** The user account manager. */
	private final UserAccountManager userAccountManager;

	/** The concrete order repo. */
	private final ConcreteOrderRepository concreteOrderRepo;

	/** The order manager. */
	private final OrderManager<Order> orderManager;

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
			ConcreteInventory<InventoryItem> concreteInventory, ConcreteProductRepository concreteProductRepo,
			ConcreteUserAccountRepository concreteAccountRepo, UserAccountManager userAccountManager,
			ConcreteOrderRepository concreteOrderRepo, OrderManager<Order> orderManager) {
		this.catalog = catalog;
		this.inventory = inventory;
		this.concreteInventory = concreteInventory;
		this.concreteProductRepo = concreteProductRepo;
		this.concreteAccountRepo = concreteAccountRepo;
		this.userAccountManager = userAccountManager;
		this.concreteOrderRepo = concreteOrderRepo;
		this.orderManager = orderManager;
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

	/**
	 * Gets the concrete product repository.
	 *
	 * @return the concrete product repository
	 */
	public ConcreteProductRepository getConcreteProductRepository() {
		return this.concreteProductRepo;
	}

	/**
	 * Gets the concrete user accoutn repository.
	 *
	 * @return the concrete user accoutn repository
	 */
	public ConcreteUserAccountRepository getConcreteUserAccountRepository() {
		return this.concreteAccountRepo;
	}

	/**
	 * Gets the user account manager.
	 *
	 * @return the user account manager
	 */
	public UserAccountManager getUserAccountManager() {
		return this.userAccountManager;
	}

	/**
	 * Gets the concrete order repository.
	 *
	 * @return the concrete order repository
	 */
	public ConcreteOrderRepository getConcreteOrderRepository() {
		return this.concreteOrderRepo;
	}
}
