package internetkaufhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
    public Iterable<ConcreteProduct> getCategory(String cat){
    	return searchType.get(cat);
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
