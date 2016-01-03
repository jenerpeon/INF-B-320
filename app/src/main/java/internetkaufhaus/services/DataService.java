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
import internetkaufhaus.repositories.ConcreteOrderRepository;
import internetkaufhaus.repositories.ConcreteProductRepository;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@Service
public class DataService {

	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
	private final ConcreteProductRepository concreteProductRepo;
	private final ConcreteUserAccountRepository concreteAccountRepo;
	private final UserAccountManager userAccountManager;
	private final ConcreteOrderRepository concreteOrderRepo;
	private final OrderManager<Order> orderManager;

	@Autowired
	public DataService(Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, 
			ConcreteProductRepository concreteProductRepo, ConcreteUserAccountRepository concreteAccountRepo,
			UserAccountManager userAccountManager, ConcreteOrderRepository concreteOrderRepo,
			OrderManager<Order> orderManager){
		this.catalog = catalog;
		this.inventory = inventory;
		this.concreteProductRepo = concreteProductRepo;
		this.concreteAccountRepo = concreteAccountRepo;
		this.userAccountManager = userAccountManager;
		this.concreteOrderRepo = concreteOrderRepo;
		this.orderManager = orderManager;
	}
	
	public OrderManager<Order> getOrderManager(){
		return this.orderManager;
	}
	
	public Catalog<ConcreteProduct> getCatalog(){
		return this.catalog;
	}
	
	public Inventory<InventoryItem> getInventory(){
		return this.inventory;
	}
	
	public ConcreteProductRepository getConcreteProductRepository(){
		return this.concreteProductRepo;
	}
	
	public ConcreteUserAccountRepository getConcreteUserAccoutnRepository(){
		return this.concreteAccountRepo;
	}
	
	public UserAccountManager getUserAccountManager(){
		return this.userAccountManager;
	}
	
	public ConcreteOrderRepository getConcreteOrderRepository(){
		return this.concreteOrderRepo;
	}
}
