package internetkaufhaus;

import static org.salespointframework.core.Currencies.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.salespointframework.catalog.*;
import org.salespointframework.order.*;
import org.salespointframework.order.OrderManager;
import org.javamoney.moneta.*;

import internetkaufhaus.model.AccountAdministration;
import internetkaufhaus.model.ConcreteMailSender;
import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.ConcreteProductRepository;
import internetkaufhaus.model.ConcreteUserAccount;
import internetkaufhaus.model.search;
import internetkaufhaus.model.ConcreteUserAccountRepository;
import org.springframework.mail.MailSender;

@Component
public class Initialize implements DataInitializer{
	private final ConcreteUserAccountRepository ConcreteUserAccountManager;
	private final UserAccountManager userAccountManager;
    private final Inventory<InventoryItem> inventory;
    private final Catalog<ConcreteProduct> productCatalog;
    private final OrderManager<Order> orderManager;
    private final AccountAdministration accountAdministration;
    private final ConcreteProductRepository concreteProductRepository;
    private final MailSender sender;

    private final search productSearch;   
    
	@Autowired
	public Initialize(Catalog<ConcreteProduct> productCatalog,UserAccountManager userAccountManager, 
	        ConcreteUserAccountRepository ConcreteUserAccountManager, Inventory<InventoryItem> inventory, 
	        OrderManager<Order> orderManager, search productSearch, AccountAdministration accountAdministration, MailSender sender, ConcreteProductRepository concreteProductRepository) {
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
		this.accountAdministration.setMailSender(this.sender);
	}

	@Override
	public void initialize() {
		//fill the user database
		initializeUsers(userAccountManager, ConcreteUserAccountManager);
		//fill the Catalog with Items
		initializeCatalog(productCatalog, inventory, productSearch, concreteProductRepository);
		//fill inventory with Inventory items
		//Inventory Items consist of one ConcreteProduct and a number representing the stock
		initializeInventory(productCatalog,inventory);

	}


	private void initializeCatalog(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory, search productSearch, ConcreteProductRepository concreteProductRepository){
		//prevents the Initializer to run in case of data persistance
        if(productCatalog.count()>0){
        	return;
        }
     
        ConcreteProduct p1 = new ConcreteProduct("Delikatesse 1", Money.of(0.99, EURO), "Delikatessen", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        ConcreteProduct p2 = new ConcreteProduct("Delikatesse 2", Money.of(0.99, EURO), "Delikatessen", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        ConcreteProduct p3 = new ConcreteProduct("Delikatesse 3", Money.of(0.99, EURO), "Delikatessen", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        
        
        ConcreteProduct p4 = new ConcreteProduct("Wein 1", Money.of(0.99, EURO), "Wein und Gurmet", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        ConcreteProduct p5 = new ConcreteProduct("Wein 2", Money.of(0.99, EURO), "Wein und Gurmet", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        ConcreteProduct p6 = new ConcreteProduct("Wein 3", Money.of(0.99, EURO), "Wein und Gurmet", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        
        ConcreteProduct p7 = new ConcreteProduct("Zigarre 1", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        ConcreteProduct p8 = new ConcreteProduct("Zigarre 2", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        ConcreteProduct p9 = new ConcreteProduct("Zigarre 3", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        ConcreteProduct p10 = new ConcreteProduct("Zigarre 4", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        ConcreteProduct p11 = new ConcreteProduct("Zigarre 5", Money.of(0.99, EURO), "Tabakwaren", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren", "https://eng.wikipedia.org/wiki/Fuzz");		
        
        List<ConcreteProduct> prods = 
        		new ArrayList<ConcreteProduct>(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11)); 
        productCatalog.save(prods);
        concreteProductRepository.save(prods);
        
        System.out.println("debugprodpage:"+concreteProductRepository.findByCategory("Tabakwaren",new PageRequest(1, 5)).toString());
        
        System.out.println("######################");
        System.out.println("debugprodpage:"+concreteProductRepository.findAll(new PageRequest(1, 5)));
        
        System.out.println("######################");
        System.out.println("debugprodpage:"+concreteProductRepository.findByCategory("Tabakwaren",new PageRequest(0, 2)).getTotalPages());
        
        System.out.println("######################");

       
        productSearch.addProds(productCatalog.findAll());
	}

	private void initializeInventory(Catalog<ConcreteProduct> productCatalog, Inventory<InventoryItem> inventory){
 		//prevents the Initializer to run in case of data persistance
        for (ConcreteProduct prod : productCatalog.findAll()) {
		    InventoryItem inventoryItem = new InventoryItem(prod, Quantity.of(10));
		    inventory.save(inventoryItem);
		}
	}

	private void initializeUsers(UserAccountManager userAccountManager, ConcreteUserAccountRepository ConcreteUserAccountManager) {
		//prevents the Initializer to run in case of data persistance
		if (userAccountManager.findByUsername("peon").isPresent()) {
			return;
		}

    final Role adminRole = Role.of("ROLE_ADMIN");
    final Role customerRole = Role.of("ROLE_CUSTOMER");
   	final Role employeeRole = Role.of("ROLE_EMPLOYEE");

		List<ConcreteUserAccount>  userAccounts= new ArrayList<ConcreteUserAccount>();
		userAccounts.add(new ConcreteUserAccount("peon","peon", adminRole,userAccountManager));
		userAccounts.add(new ConcreteUserAccount("saul", "saul", employeeRole, userAccountManager));
	  userAccounts.add(new ConcreteUserAccount("admin", "admin", customerRole, userAccountManager));
    userAccounts.add(new ConcreteUserAccount("behrens_lars@gmx.de", "any", "Skywalker", "Tatooine Outa RIM", "dark",adminRole, userAccountManager));
	  for(ConcreteUserAccount acc : userAccounts){
    		userAccountManager.save(acc.getUserAccount());
		    ConcreteUserAccountManager.save(acc);
	  }	
	}
}
