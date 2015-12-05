package internetkaufhaus.model;

import static org.junit.Assert.assertTrue;
import static org.salespointframework.core.Currencies.EURO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;
import internetkaufhaus.Initialize;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class StockManagerTest {
	
	private ConcreteProduct model;

	@Autowired ConcreteProductRepository productCatalog;
	@Autowired Inventory<InventoryItem> inventory;
	@Autowired Search productSearch;
	
	@Before
	public void init(){
		model = new ConcreteProduct("Zigarre", Money.of(1.99, EURO), "Tabakwaren", "Eine gute alte Zigarre", "www.tabak.ru", "zigarre.png");	
		
		InventoryItem inventoryItem = new InventoryItem(model, Quantity.of(10));
		inventory.save(inventoryItem);
	}
	
	@Test
	public void orderArticleTest(){
		ProductIdentifier prodId;
		Quantity quantity = Quantity.of(2);
		
		prodId=model.getIdentifier();		
		inventory.findByProductIdentifier(prodId ).ifPresent(x->{x.increaseQuantity(quantity);inventory.save(x);});
		
		assertTrue("increase stock", inventory.findByProduct(model).get().getQuantity().isGreaterThanOrEqualTo(Quantity.of(12)) );
		
	}
}
