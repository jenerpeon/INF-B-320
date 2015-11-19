package internetkaufhaus.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;

import internetkaufhaus.model.Comment;



@Entity
public class ConcreteProduct extends Product {
	private static final long serialVersionUID = 3602164805477720501L;

	
	private String webLink;

	@OneToMany(cascade = CascadeType.ALL) private List<Comment> comments = new LinkedList<Comment>();


	public String category;
		
    @SuppressWarnings("unused")
	private ConcreteProduct(){}
    
	public ConcreteProduct(String name, Money price, String category, String webLink){
        super(name, price);
		this.addCategory(category);
		this.webLink=webLink;

	}

	public String getWebLink() {
		return webLink;
	}

	public Iterable<Comment> getComments() {
		return comments;
	}

	
	public void addComment(Comment comment) {
		comments.add(comment);
	}

}

