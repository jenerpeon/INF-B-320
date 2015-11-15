package internetkaufhaus.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import java.util.Optional;
import java.util.Map;
import java.util.HashMap;


import org.salespointframework.inventory.*;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.BusinessTime;


import internetkaufhaus.model.Comment;
import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.ConcreteProduct.ProdType;
import internetkaufhaus.model.ProductCatalog;


@Controller
public class CatalogController {
	private static final Quantity NONE = Quantity.of(0);
    private final ProductCatalog catalog;
    private final Inventory<InventoryItem> inventory;
    private Map<ProdType, ArrayList<ConcreteProduct> > searchType = new HashMap<ProdType, ArrayList<ConcreteProduct> > ();
    
    @Autowired
    public CatalogController(ProductCatalog catalog, Inventory<InventoryItem> inventory){
    	this.catalog = catalog;
    	this.inventory = inventory;
    	
        for(ConcreteProduct prod : this.catalog.findAll()){
            ProdType type = prod.getType();
            if(!this.searchType.containsKey(type))
               this.searchType.put(type, new ArrayList<ConcreteProduct> ());
            this.searchType.get(type).add(prod);
   
        }
     
    }
    
    
    @RequestMapping("/catalog/{type}")
    public String category(@PathVariable("type") ProdType type, ModelMap model ) {
    	for(ConcreteProduct prod : catalog.findAll()) {
    		ProdType t = prod.getType();
    		if(! searchType.containsKey(t)){
    			searchType.put(t, new ArrayList<ConcreteProduct>());
    		}	
    		if(!searchType.get(t).contains(prod)){
    			searchType.get(t).add(prod);
    		}
    	}
    	model.addAttribute("catagory", searchType.get(type));
    	return "catalog";	
    }
    
    
    @RequestMapping("/detail/{prodId}")
	public String detail(@PathVariable("prodId") ConcreteProduct prod, Model model) {

		Optional<InventoryItem> item = inventory.findByProductIdentifier(prod.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
				

		model.addAttribute("concreteproduct", prod);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(NONE));
		model.addAttribute("comments", prod.getComments());
		
		

		return "detail";
	}
    
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("prodId") ConcreteProduct prod, @RequestParam("comment") String comment,
			@RequestParam("rating") int rating, Model model) {
		Comment c= new Comment(comment, rating, new Date(),"");
		if(! (comment=="")){
			
			prod.addComment(c);
			c.setFormatedDate(c.getDate());
			catalog.save(prod);
			model.addAttribute("time", c.getFormatedDate());
		}

		
		return "redirect:detail/" + prod.getIdentifier();
		
	}
}
