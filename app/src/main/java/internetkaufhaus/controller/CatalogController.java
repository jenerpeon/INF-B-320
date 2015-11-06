package internetkaufhaus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.salespointframework.inventory.*;
import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.ConcreteProduct.ProdType;
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
}
