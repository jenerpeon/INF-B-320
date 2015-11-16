package internetkaufhaus;

import static org.salespointframework.core.Currencies.*;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.salespointframework.catalog.*;

import org.javamoney.moneta.*;

import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.search;

@Component
public class Initialize implements DataInitializer{
	private final UserAccountManager userAccountManager;
    private final Inventory<InventoryItem> inventory;
    private final Catalog<ConcreteProduct> productCatalog;
    private final search productSearch;   
    
	@Autowired
	public Initialize(Catalog<ConcreteProduct> productCatalog, UserAccountManager userAccountManager, Inventory<InventoryItem> inventory, search productSearch) {
        this.inventory = inventory;
		this.userAccountManager = userAccountManager;
		this.productCatalog = productCatalog;
		this.productSearch = productSearch;
	}

	@Override
	public void initialize() {
		//fill the user database
		initializeUsers(userAccountManager);
		//fill the Catalog with Items
		initializeCatalog(productCatalog, inventory, productSearch);
		//fill inventory with Inventory items
		//Inventory Items consist of one ConcreteProduct and a number representing the stock
		initializeInventory(productCatalog,inventory);
	}

	private void initializeCatalog(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory, search productSearch){
		//prevents the Initializer to run in case of data persistance
        if(productCatalog.count()>0){
        	return;
        }
     
        ConcreteProduct p1 = new ConcreteProduct("awesome_bhaskara", Money.of(0.99, EURO), "Fuzz", "https://eng.wikipedia.org/wiki/Fuzz");		
        ConcreteProduct p2 = new ConcreteProduct("evil_newton", Money.of(0.99, EURO), "Garbage","https://eng.wikipedia.org/wiki/Fuzz");
        ConcreteProduct p3 = new ConcreteProduct("trusting_babbage", Money.of(0.99, EURO), "Trash","https://eng.wikipedia.org/wiki/Fuzz");
        
        productCatalog.save(p1);
        productCatalog.save(p2);
        productCatalog.save(p3);

        productSearch.addProds(productCatalog.findAll());

	}

	private void initializeInventory(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory){
 		//prevents the Initializer to run in case of data persistance
 		  
        for (ConcreteProduct prod : productCatalog.findAll()) {
		    InventoryItem inventoryItem = new InventoryItem(prod, Quantity.of(10));
		    inventory.save(inventoryItem);
		}
	}

	private void initializeUsers(UserAccountManager userAccountManager) {
		//prevents the Initializer to run in case of data persistance
		if (userAccountManager.findByUsername("peon").isPresent()) {
			return;
		}

        final Role adminRole = Role.of("ROLE_ADMIN");
        final Role customerRole = Role.of("ROLE_CUSTOMER");
		final Role employeeRole = Role.of("ROLE_EMPLOYEE");
		
		
		UserAccount a1 = userAccountManager.create("peon","peon", adminRole);
		UserAccount e1 = userAccountManager.create("saul", "saul", employeeRole);
		UserAccount u1 = userAccountManager.create("admin", "admin", customerRole);
      
		userAccountManager.save(a1);
        userAccountManager.save(e1);
		userAccountManager.save(u1);
	}
}
