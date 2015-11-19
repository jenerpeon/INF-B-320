package internetkaufhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class search implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private HashMap<String, ArrayList<ConcreteProduct> > searchType;
    
    public search(){
    	this.searchType = new HashMap<String, ArrayList<ConcreteProduct> >();
    }
	
    public HashMap<String, ArrayList<ConcreteProduct> > getsearchTypes() {
    	return this.searchType;
    }
    
    public Iterable<String> getCagegories(){
      return searchType.keySet();
    }
    public Iterable<ConcreteProduct> getProdsByCategory(String cat){
    	return searchType.get(cat);
    }
    
    public List<List<ConcreteProduct>> list50 (String cat){
      List<List<ConcreteProduct>> list50 = new ArrayList<List<ConcreteProduct>>();
    	List<ConcreteProduct> init = new ArrayList<ConcreteProduct>();
    	for( ConcreteProduct p: getProdsByCategory(cat)){
    		if(init.size()>=4){
    			list50.add(init);
    			init = new ArrayList<ConcreteProduct>();	
    		}
      		init.add(p);
    	}
    	list50.add(init);
		return list50;
    	
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
}
