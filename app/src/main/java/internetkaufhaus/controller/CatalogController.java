package internetkaufhaus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;
import org.salespointframework.inventory.*;
import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.ProductCatalog;

@Controller
public class CatalogController {
    private final ProductCatalog catalog;
    private final Inventory<InventoryItem> inventory;

    @Autowired
    public CatalogController(ProductCatalog catalog, Inventory<InventoryItem> inventory){
    	this.catalog = catalog;
    	this.inventory = inventory;
    }
    
    @RequestMapping(value = {"/catalog"})
    public String catalog(ModelMap modelmap){
        modelmap.addAttribute("prodlist", catalog.findAll());	
    	return "catalog";
    }
}
