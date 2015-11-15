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

	public static enum ProdType{
		Fuzz, Trash, Garbage;
	}

	private ProdType type;
	
	private String webLink;

	@OneToMany(cascade = CascadeType.ALL) private List<Comment> comments = new LinkedList<Comment>();
		
    @SuppressWarnings("unused")
	private ConcreteProduct(){}
    
	public ConcreteProduct(String name, Money price, ProdType type, String webLink){
        super(name, price);
		this.type=type;
		this.webLink=webLink;
	}

	public String getWebLink() {
		return webLink;
	}

	

	public ProdType getType() {
		return type;
	}

	public void setType(ProdType type) {
		this.type = type;
	}
	
	public Iterable<Comment> getComments() {
		return comments;
	}

	
	public void addComment(Comment comment) {
		comments.add(comment);
	}

}

