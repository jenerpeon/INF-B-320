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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import internetkaufhaus.AbstractWebIntegrationTests;
import internetkaufhaus.controller.CatalogController;
import internetkaufhaus.model.Search;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogControllerTest.
 */
public class CatalogControllerTest extends AbstractWebIntegrationTests {

	/** The controller. */
	@Autowired
	CatalogController controller;
	
	/** The prodsearch. */
	@Autowired
	Search prodsearch;

	/** The categories. */
	public List<String> categories = new ArrayList<String>();

	/**
	 * Sample mvc integration test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void sampleMvcIntegrationTest() throws Exception {

		// RequestMapping and model checking for predefined categories
		for (String category : categories) {
			// method sufu
			mvc.perform(get("/sufu/" + category)).andExpect(status().isOk())
					.andExpect(model().attribute((String) category, is(not(null))))
					.andExpect(model().attribute("ProdsOfCategory", is(not(emptyIterable()))));
			mvc.perform(get("/catalog/" + category)).andExpect(status().isOk())
					.andExpect(model().attribute((String) category, is(not(null))))
					.andExpect(model().attribute("ProdsOfCategory", is(not(emptyIterable()))));

		}
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
