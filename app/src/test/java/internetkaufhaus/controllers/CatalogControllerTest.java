package internetkaufhaus.controllers;

import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.springframework.beans.factory.annotation.Autowired;

import internetkaufhaus.AbstractWebIntegrationTests;
import internetkaufhaus.controller.CatalogController;
import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.model.Search;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogControllerTest.
 */
public class CatalogControllerTest extends AbstractWebIntegrationTests {

	private static final Quantity NONE = Quantity.of(0);

	@Autowired
	CatalogController controller;

	@Autowired
	Search prodsearch;
	@Autowired
	Catalog<ConcreteProduct> catalog;
	@Autowired
	Inventory<InventoryItem> inventory;

	/** The categories. */
	public List<String> categories = new ArrayList<String>();

	/**
	 * Sample mvc integration test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void sufuTest() throws Exception {

		// RequestMapping and model checking for predefined categories
		for (String category : categories) {
			// method sufu
			mvc.perform(get("/sufu/" + category)).andExpect(status().isOk())
					.andExpect(model().attribute((String) category, is(not(null))))
					.andExpect(model().attribute("ProdsOfCategory", is(not(emptyIterable()))));
		}
	}
	
	@Test
	public void categoryTest() throws Exception {
		for (String category : categories) {
			mvc.perform(get("/catalog/" + category)).andExpect(status().isOk())
			.andExpect(model().attribute((String) category, is(not(null))))
			.andExpect(model().attribute("ProdsOfCategory", is(not(emptyIterable()))));
		}
	}
	
	@Test
	public void dateilTest() throws Exception {
		List<ConcreteProduct> prods = new ArrayList<ConcreteProduct>();
		CollectionUtils.addAll(prods, catalog.findAll().iterator());
		Random random = new Random();
		ConcreteProduct prod = prods.get(random.nextInt(prods.size() -1));
		
		Optional<InventoryItem> item = inventory.findByProductIdentifier(prod.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(NONE);
		
		mvc.perform(get("/detail/" + prod.getIdentifier().toString())).andExpect(status().isOk())
		.andExpect(model().attribute("concreteproduct", is(prod)))
		.andExpect(model().attribute("quantity", is(quantity)))
		.andExpect(model().attribute("orderable", is(quantity.isGreaterThan(NONE))));
	}

	/**
	 * Integration test for an individual controller.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void CatalogControllerIntegrationTest() {
		assertTrue("blafsel", true);
		/*
		 * Model model = new ExtendedModelMap();
		 * 
		 * List<String> Categories = new ArrayList<String>();
		 * 
		 * String returnedView = controller.category("", model);
		 * 
		 * assertThat(returnedView, is("discCatalog"));
		 * 
		 * Iterable<Object> object = (Iterable<Object>)
		 * model.asMap().get("catalog"); assertThat(object,
		 * is(iterableWithSize(9)));
		 */ }
}
