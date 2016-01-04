//package internetkaufhaus.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.hamcrest.Matchers.emptyIterable;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.not;
//import static org.junit.Assert.assertTrue;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import internetkaufhaus.AbstractWebIntegrationTests;
//import internetkaufhaus.controller.AdminController;
//
//public class AdminControllerTest extends AbstractWebIntegrationTests{
//
//	@Autowired 
//	AdminController controller;
//	
//	public void MVCIntegrationTest() throws Exception
//	{
//		mvc.perform(get("/admin/changeuser")).andExpect(status().isOk())
//		.andExpect(model().attribute("customers", is(not(emptyIterable()))));
//		;
//	}
//}
