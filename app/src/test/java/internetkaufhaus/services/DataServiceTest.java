package internetkaufhaus.services;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;

import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class DataServiceTest.
 */
public class DataServiceTest {

	/** The data service. */
	private DataService dataService;

	/** The catalog. */
	@Autowired
	private Catalog<ConcreteProduct> catalog;

	/** The inventory. */
	@Autowired
	private Inventory<InventoryItem> inventory;

	/** The concrete product repo. */
	@Autowired
	private ConcreteProductRepository concreteProductRepo;

	/** The concrete account repo. */
	@Autowired
	private ConcreteUserAccountRepository concreteAccountRepo;

	/** The user account manager. */
	@Autowired
	private UserAccountManager userAccountManager;

	/** The concrete order repo. */
	@Autowired
	private ConcreteOrderRepository concreteOrderRepo;

	/** The order manager. */
	@Autowired
	private OrderManager<Order> orderManager;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		this.dataService = new DataService(this.catalog, this.inventory, this.concreteProductRepo,
				this.concreteAccountRepo, this.userAccountManager, this.concreteOrderRepo, this.orderManager);
	}

	/**
	 * Test get catalog.
	 */
	@Test
	public void testGetCatalog() {
		assertEquals("testGetCatalog", this.catalog, this.dataService.getCatalog());
	}

	/**
	 * Test get inventory.
	 */
	@Test
	public void testGetInventory() {
		assertEquals("testGetInventory", this.inventory, this.dataService.getInventory());
	}

	/**
	 * Test get concrete product repo.
	 */
	@Test
	public void testGetConcreteProductRepo() {
		assertEquals("testGetConcreteProductRepo", this.concreteProductRepo,
				this.dataService.getConcreteProductRepository());
	}

	/**
	 * Test get concrete account repo.
	 */
	@Test
	public void testGetConcreteAccountRepo() {
		assertEquals("testGetConcreteAccountRepo", this.concreteAccountRepo,
				this.dataService.getConcreteUserAccoutnRepository());
	}

	/**
	 * Test get user account manager.
	 */
	@Test
	public void testGetUserAccountManager() {
		assertEquals("testGetUserAccountManager", this.userAccountManager, this.dataService.getUserAccountManager());
	}

	/**
	 * Test get concrete order repo.
	 */
	@Test
	public void testGetConcreteOrderRepo() {
		assertEquals("testGetConcreteOrderRepo", this.concreteOrderRepo, this.dataService.getConcreteOrderRepository());
	}

	/**
	 * Test get order manager.
	 */
	@Test
	public void testGetOrderManager() {
		assertEquals("testGetOrderManager", this.orderManager, this.dataService.getOrderManager());
	}
}
