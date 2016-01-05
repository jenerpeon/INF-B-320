package internetkaufhaus.controller;

import java.util.List;

import org.salespointframework.catalog.Catalog;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import internetkaufhaus.entities.ConcreteProduct;
import internetkaufhaus.repositories.ConcreteProductRepository;

/**
 * This is the shop controller. It controls the shop. Or maybe it shops the
 * controls? You never know... In this class you may find the controllers for
 * general pages, should you choose to look for them.
 * 
 * @author max
 *
 */
@Controller
@SessionAttributes("cart")
public class ShopController {

	private final Catalog<ConcreteProduct> catalog;
	private final UserAccountManager userAccountManager;
	private final ConcreteProductRepository concreteCatalog;

	/**
	 * This is the constructor. It's neither used nor does it contain any
	 * functionality other than storing function arguments as class attribute,
	 * what do you expect me to write here?
	 * 
	 * @param userAccountManager
	 * @param catalog
	 */
	@Autowired
	public ShopController(UserAccountManager userAccountManager, Catalog<ConcreteProduct> catalog,
			ConcreteProductRepository concreteCatalog) {
		this.userAccountManager = userAccountManager;
		this.catalog = catalog;
		this.concreteCatalog = concreteCatalog;
	}

	/**
	 * This is a Request Mapping. It Maps Requests. Or does it Request Maps?
	 * This page shows the main page.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/", "/index" })
	public String index(ModelMap model) {
		model.addAttribute("prodList", catalog.findAll());
		/*
		 * List<ConcreteProduct> bannerContent = concreteCatalog .findAll( new
		 * PageRequest(0, 10, new Sort(new Sort.Order(Sort.Direction.DESC,
		 * "RANDOM()", Sort.NullHandling.NATIVE), new
		 * Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE))))
		 * .getContent(); model.addAttribute("bannerContent", bannerContent);
		 */
		List<ConcreteProduct> top5rated = concreteCatalog
				.findAll(
						new PageRequest(0, 5,
								new Sort(new Sort.Order(Sort.Direction.DESC, "averageRating", Sort.NullHandling.NATIVE),
										new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE))))
				.getContent();
		model.addAttribute("top5rated", top5rated);
		List<ConcreteProduct> top5sold = concreteCatalog
				.findAll(
						new PageRequest(0, 5,
								new Sort(
										new Sort.Order(Sort.Direction.DESC, "amountProductsSold",
												Sort.NullHandling.NATIVE),
										new Sort.Order(Sort.Direction.ASC, "name", Sort.NullHandling.NATIVE))))
				.getContent();
		model.addAttribute("top5sold", top5sold);
		return "index";
	}

	/**
	 * TODO: What does this do?
	 * 
	 * @return
	 */
	public UserAccountManager getUserAccountManager() {
		return userAccountManager;
	}

}
