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

public class DataServiceTest {

	private DataService dataService;

	@Autowired
	private Catalog<ConcreteProduct> catalog;

	@Autowired
	private Inventory<InventoryItem> inventory;

	@Autowired
	private ConcreteProductRepository concreteProductRepo;

	@Autowired
	private ConcreteUserAccountRepository concreteAccountRepo;

	@Autowired
	private UserAccountManager userAccountManager;

	@Autowired
	private ConcreteOrderRepository concreteOrderRepo;

	@Autowired
	private OrderManager<Order> orderManager;

	@Before
	public void init() {
		this.dataService = new DataService(this.catalog, this.inventory, this.concreteProductRepo,
				this.concreteAccountRepo, this.userAccountManager, this.concreteOrderRepo, this.orderManager);
	}

	@Test
	public void testGetCatalog() {
		assertEquals("testGetCatalog", this.catalog, this.dataService.getCatalog());
	}

	@Test
	public void testGetInventory() {
		assertEquals("testGetInventory", this.inventory, this.dataService.getInventory());
	}

	@Test
	public void testGetConcreteProductRepo() {
		assertEquals("testGetConcreteProductRepo", this.concreteProductRepo,
				this.dataService.getConcreteProductRepository());
	}

	@Test
	public void testGetConcreteAccountRepo() {
		assertEquals("testGetConcreteAccountRepo", this.concreteAccountRepo,
				this.dataService.getConcreteUserAccoutnRepository());
	}

	@Test
	public void testGetUserAccountManager() {
		assertEquals("testGetUserAccountManager", this.userAccountManager, this.dataService.getUserAccountManager());
	}

	@Test
	public void testGetConcreteOrderRepo() {
		assertEquals("testGetConcreteOrderRepo", this.concreteOrderRepo, this.dataService.getConcreteOrderRepository());
	}

	@Test
	public void testGetOrderManager() {
		assertEquals("testGetOrderManager", this.orderManager, this.dataService.getOrderManager());
	}
}
