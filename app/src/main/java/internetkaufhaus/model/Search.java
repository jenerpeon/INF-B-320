package internetkaufhaus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.salespointframework.catalog.Catalog;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import internetkaufhaus.entities.ConcreteProduct;

@Component
public class Search implements Serializable {

	/**
	 * The Search class is made for finding and working on products.
	 *
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<String, ArrayList<ConcreteProduct>> searchType;
	private Catalog<ConcreteProduct> catalog;

	public Search() {
		this.searchType = new HashMap<String, ArrayList<ConcreteProduct>>();
	}

	public HashMap<String, ArrayList<ConcreteProduct>> getsearchTypes() {
		return this.searchType;
	}

	@ModelAttribute("categories")
	public Iterable<String> getCategories() {
		return searchType.keySet();
	}

	/**
	 * The getProdsByCategory method is made for finding products via product
	 * category.
	 * 
	 * @param cat
	 *            existing category as String
	 * @return List of all products with given category
	 */
	public List<ConcreteProduct> getProdsByCategory(String cat) {
		return searchType.get(cat);
	}

	public void setCatalog(Catalog<ConcreteProduct> catalog) {
		this.catalog = catalog;
	}

	/**
	 * This comment is just here because sonarcube is a little bitch.
	 */
	public void getComments() {
		// TODO: this needs to be implemented.
	}

	/**
	 * The list50 method generates a list of lists in which are exactly ten
	 * elements each. This is necessary to structure the product catalog.
	 * 
	 * @param prods
	 *            Iterable filled with products
	 * @return List of Lists with four products each
	 */
	public List<List<ConcreteProduct>> list50(Iterable<ConcreteProduct> prods) {
		List<List<ConcreteProduct>> list50 = new ArrayList<List<ConcreteProduct>>();
		List<ConcreteProduct> init = new ArrayList<ConcreteProduct>();
		for (ConcreteProduct p : prods) {
			if (init.size() >= 10) {
				list50.add(init);
				init = new ArrayList<ConcreteProduct>();
			}
			init.add(p);
		}
		list50.add(init);
		return list50;

	}

	public List<ConcreteProduct> lookup_bar(String str) {
		if (str.isEmpty()) {
			List<ConcreteProduct> lookup = new ArrayList<ConcreteProduct>();
			for (ConcreteProduct prod : catalog.findAll()) {
				lookup.add(prod);
			}
			return lookup;
		}
		List<ConcreteProduct> lookup = new ArrayList<ConcreteProduct>();
		for (ConcreteProduct prod : catalog.findAll()) {
			if (prod.getName().toLowerCase().contains(str.toLowerCase())) {
				lookup.add(prod);
			}
		}
		return lookup;
	}

	public List<ConcreteProduct> lookup_bar(String str, int limit) {
		if (str.isEmpty()) {
			List<ConcreteProduct> lookup = new ArrayList<ConcreteProduct>();
			for (ConcreteProduct prod : catalog.findAll()) {
				if (limit > 0) {
					limit--;
				} else {
					break;
				}
				lookup.add(prod);
			}
			return lookup;
		}
		List<ConcreteProduct> lookup = new ArrayList<ConcreteProduct>();
		for (ConcreteProduct prod : catalog.findAll()) {
			if (prod.getName().toLowerCase().contains(str.toLowerCase())) {
				if (limit > 0) {
					limit--;
				} else {
					break;
				}
				lookup.add(prod);
			}
		}
		return lookup;
	}

	public void addProds(Iterable<ConcreteProduct> iterable) {
		for (ConcreteProduct prod : iterable) {
			for (String cat : prod.getCategories()) {
				if (!this.searchType.containsKey(cat)) {
					this.searchType.put(cat, new ArrayList<ConcreteProduct>());
				}
				this.searchType.get(cat).add(prod);
			}
		}
	}

	public void delete(ConcreteProduct prodId) {
		HashMap<String, ArrayList<ConcreteProduct>> list = getsearchTypes();
		Iterable<String> cats = prodId.getCategories();

		for (String category : cats) {
			list.get(category).remove(prodId);

		}
		// TODO Auto-generated method stub

	}

}
