package internetkaufhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.salespointframework.catalog.Catalog;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
public class search implements Serializable {

	private static final long serialVersionUID = 1L;
<<<<<<< HEAD
   
	private HashMap<String, ArrayList<ConcreteProduct> > searchType;
	private Catalog<ConcreteProduct> catalog;
    
    public search(){
    	this.searchType = new HashMap<String, ArrayList<ConcreteProduct> >();
    }
	
    public HashMap<String, ArrayList<ConcreteProduct> > getsearchTypes() {
    	return this.searchType;
    }
    
    @ModelAttribute("categories") 
    public Iterable<String> getCagegories(){
      return searchType.keySet();
    }
    public Iterable<ConcreteProduct> getProdsByCategory(String cat){
    	return searchType.get(cat);
    }
    
    public void setCatalog(Catalog<ConcreteProduct> catalog){
        this.catalog = catalog;
    }
    
    public List<List<ConcreteProduct>> list50 (Iterable<ConcreteProduct> prods){
      List<List<ConcreteProduct>> list50 = new ArrayList<List<ConcreteProduct>>();
    	List<ConcreteProduct> init = new ArrayList<ConcreteProduct>();
    	for( ConcreteProduct p: prods){
    		if(init.size()>=4){
    			list50.add(init);
    			init = new ArrayList<ConcreteProduct>();	
    		}
      		init.add(p);
    	}
    	list50.add(init);
		return list50;
    	
    }
    public Iterable<ConcreteProduct> lookup_bar(String str){
        if(str.isEmpty())
            return catalog.findAll();          
        List<ConcreteProduct> lookup = new ArrayList<ConcreteProduct>();
        for(ConcreteProduct prod : catalog.findAll()){
            if(prod.getName().toLowerCase().contains(str.toLowerCase())){
               lookup.add(prod); 
            }
        }
        return lookup;
    }
  
      public void addProds(Iterable<ConcreteProduct> iterable){
        for(ConcreteProduct prod : iterable){
            for(String cat : prod.getCategories()){
            	System.out.println(cat);
                if(!this.searchType.containsKey(cat)){
                	this.searchType.put(cat, new ArrayList<ConcreteProduct>());
                }
                this.searchType.get(cat).add(prod);
            }
        }
    }
=======

	private HashMap<String, ArrayList<ConcreteProduct>> searchType;

	public search() {
		this.searchType = new HashMap<String, ArrayList<ConcreteProduct>>();
	}

	public HashMap<String, ArrayList<ConcreteProduct>> getsearchTypes() {
		return this.searchType;
	}

	public Iterable<String> getCagegories() {
		return searchType.keySet();
	}

	public Iterable<ConcreteProduct> getProdsByCategory(String cat) {
		return searchType.get(cat);
	}

	public List<List<ConcreteProduct>> list50(String cat) {
		List<List<ConcreteProduct>> list50 = new ArrayList<List<ConcreteProduct>>();
		List<ConcreteProduct> init = new ArrayList<ConcreteProduct>();
		for (ConcreteProduct p : getProdsByCategory(cat)) {
			if (init.size() >= 2) {
				list50.add(init);
				init = new ArrayList<ConcreteProduct>();
			}
			init.add(p);
		}
		list50.add(init);
		return list50;

	}

	public void addProds(Iterable<ConcreteProduct> iterable) {
		for (ConcreteProduct prod : iterable) {
			for (String cat : prod.getCategories()) {
				System.out.println(cat);
				if (!this.searchType.containsKey(cat)) {
					this.searchType.put(cat, new ArrayList<ConcreteProduct>());
				}
				this.searchType.get(cat).add(prod);
			}
		}
	}

	public void delete(ConcreteProduct prodId) {
		HashMap<String, ArrayList<ConcreteProduct>> list= getsearchTypes();
		Iterable<String> cats=prodId.getCategories();
		
		for( String category : cats){
			list.get(category).remove(prodId);
			
		}
		// TODO Auto-generated method stub
		
	}

	
>>>>>>> articlemanagement
}
