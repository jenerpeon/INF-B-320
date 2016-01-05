package internetkaufhaus.model;

import java.util.List;

import org.springframework.stereotype.Component;

import internetkaufhaus.entities.ConcreteProduct;

// TODO: Auto-generated Javadoc
/**
 * The Class StartPage.
 */
@Component
public class StartPage {

	/** The banner products. */
	private List<ConcreteProduct> bannerProducts;

	/** The selection products. */
	private List<ConcreteProduct> selectionProducts;

	/**
	 * Instantiates a new start page.
	 */
	public StartPage() {
		System.out.print("");
	}

	/**
	 * Instantiates a new start page.
	 */
	public StartPage(List<ConcreteProduct> bannerProducts, List<ConcreteProduct> selectionProducts) {
		this.bannerProducts = bannerProducts;
		this.selectionProducts = selectionProducts;
	}

	/**
	 * Gets the banner products.
	 *
	 * @return the banner products
	 */
	public List<ConcreteProduct> getBannerProducts() {
		return bannerProducts;
	}

	/**
	 * Sets the banner products.
	 *
	 * @param bannerProducts
	 *            the new banner products
	 */
	public void setBannerProducts(List<ConcreteProduct> bannerProducts) {
		this.bannerProducts = bannerProducts;
	}

	/**
	 * Gets the selection products.
	 *
	 * @return the selection products
	 */
	public List<ConcreteProduct> getSelectionProducts() {
		return selectionProducts;
	}

	/**
	 * Sets the selection products.
	 *
	 * @param selectionProducts
	 *            the new selection products
	 */
	public void setSelectionProducts(List<ConcreteProduct> selectionProducts) {
		this.selectionProducts = selectionProducts;
	}

	/**
	 * Adds a banner product.
	 *
	 * @param product
	 *            the product
	 */
	public void addBannerProduct(ConcreteProduct product) {
		this.bannerProducts.add(product);
	}

	/**
	 * Adds a selection product.
	 *
	 * @param product
	 *            the product
	 */
	public void addSelectionProduct(ConcreteProduct product) {
		this.selectionProducts.add(product);
	}
}
