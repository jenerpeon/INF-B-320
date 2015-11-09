package internetkaufhaus.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;


import org.salespointframework.inventory.*;
import org.salespointframework.quantity.Quantity;

import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.ConcreteProduct.ProdType;

import internetkaufhaus.model.ProductCatalog;


@Controller
public class CatalogController {
	private static final Quantity NONE = Quantity.of(0);
    private final ProductCatalog catalog;
    private final Inventory<InventoryItem> inventory;
    private Map<ProdType, ArrayList<ConcreteProduct> > searchType = new HashMap<ProdType, ArrayList<ConcreteProduct> > ();
    private List<ConcreteProduct> prods = new ArrayList<ConcreteProduct> ();

    @Autowired
    public CatalogController(ProductCatalog catalog, Inventory<InventoryItem> inventory){
    	this.catalog = catalog;
    	this.inventory = inventory;
    	
        for(ConcreteProduct prod : this.catalog.findAll()){
            ProdType type = prod.getType();
            ArrayList<ConcreteProduct> cache;
            if(!this.searchType.containsKey(type))
               this.searchType.put(type, new ArrayList<ConcreteProduct> ());
            this.searchType.get(type).add(prod);
        }
    }
    
    @RequestMapping("/detail/{prodId}")
	public String detail(@PathVariable("prodId") ConcreteProduct prod, Model model) {

		Optional<InventoryItem> item = inventory.findByProductIdentifier(prod.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
		System.out.println(prod);		

		model.addAttribute("concreteproduct", catalog.findOne(prod.getIdentifier()));
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));
		

		return "detail";
	}
}
