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
    public Iterable<ConcreteProduct> getCategory(String cat){
    	return searchType.get(cat);
    }
    
    public List<List<ConcreteProduct>> list50 (String cat){
    	List<List<ConcreteProduct>> list= new ArrayList<List<ConcreteProduct>>();
    	List<ConcreteProduct> init = new ArrayList<ConcreteProduct>();
    	list.add(init);
    	for( ConcreteProduct p: getCategory(cat)){
    		if(init.size()>=4){
    			init = new ArrayList<ConcreteProduct>();	
    			list.add(init);
    		}
      		init.add(p);
    	}
    		
		return list;
    	
    }
   
  
    //List with number of how many 4 Products e.g. 1= one four group, 2=two four group of products 
    public ArrayList<Integer> numbers (String cat){
    	int k=0, i=1;
    	ArrayList<Integer> numbers = new ArrayList<Integer>();
    	for (ConcreteProduct p: getCategory(cat)){
    		k++;
    	
    		if(k==4){
    			numbers.add(i);
    			i++;
    			k=0;
    		}
    	}
    	if(k>0) numbers.add(i);
    	return numbers;
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
