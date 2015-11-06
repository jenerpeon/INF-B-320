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

    @Autowired
    public CatalogController(ProductCatalog catalog, Inventory<InventoryItem> inventory){
    	this.catalog = catalog;
    	this.inventory = inventory;
    }
    
    @RequestMapping(value = {"/fuzzCatalog"})
    public String fuzzcatalog(ModelMap modelmap){
    	
        modelmap.addAttribute("catalog",catalog.findByType(ProdType.Fuzz));
    	return "catalog";
    }
    
    @RequestMapping(value = {"/trashCatalog"})
    public String trashcatalog(ModelMap modelmap){
   
        modelmap.addAttribute("catalog",catalog.findByType(ProdType.Trash));
    	return "catalog";
    }
    @RequestMapping(value = {"/garbageCatalog"})
    public String garbagecatalog(ModelMap modelmap){
   
        modelmap.addAttribute("catalog",catalog.findByType(ProdType.Garbage));
    	return "catalog";
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
