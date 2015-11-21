package internetkaufhaus.controller;

import java.awt.Component;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;
import javax.validation.Valid;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import internetkaufhaus.forms.EditArticleForm;
import internetkaufhaus.model.ConcreteProduct;
import internetkaufhaus.model.search;

@Controller
@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
public class ManagementController {

	
	private static final Quantity NONE = Quantity.of(0);
	private final Catalog<ConcreteProduct> catalog;
	private final Inventory<InventoryItem> inventory;
	private final search prodSearch;


	@Autowired
	public ManagementController(Catalog<ConcreteProduct> catalog, Inventory<InventoryItem> inventory, search prodSearch) {
		this.catalog = catalog;
		this.inventory = inventory;
		this.prodSearch = prodSearch;
		

	}

	@RequestMapping("/management")
	public String articleManagement(Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("prod50", catalog.findAll());
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("inventory", inventory);
		
		return "changecatalog";
	}

	@RequestMapping("/management/addArticle")
	public String addArticle(Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("categories", prodSearch.getCagegories());
		return "changecatalognewitem";
	}

	@RequestMapping("/management/editArticle/{prodId}")
	public String editArticle(@PathVariable("prodId") ConcreteProduct prod, Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("price", prod.getPrice().getNumber());
		return "changecatalogchangeitem";
	}

	@RequestMapping(value = "/management/editedArticle", method = RequestMethod.POST)
	public String editedArticle(@ModelAttribute("editArticleForm") @Valid EditArticleForm editForm, @RequestParam("image") MultipartFile img, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/management/editArticle/";
		}

		if (!img.isEmpty()) {
			try {
				byte[] bytes = img.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("filename"))); // TODO:
																													// generate
																													// filename
				stream.write(bytes);
				stream.close();
				System.out.println("success (yay) !!!"); // TODO: validation
			} catch (Exception e) {
				System.out.println("error (" + e.getMessage() + ") !!!");
			}
		} else {
			System.out.println("another error (file empty) !!!");
		}
		
		ConcreteProduct prodId = editForm.getProdId();
		prodId.addCategory(editForm.getCategory());
		prodId.setName(editForm.getName());
		prodId.setPrice(Money.of(editForm.getPrice(), "EUR"));
		prodId.setDescription(editForm.getDescription());
		
		if(!(img.getOriginalFilename().isEmpty())){
			prodId.setImagefile(img.getOriginalFilename());
		}
		
		
		prodSearch.delete(prodId);	
		catalog.save(prodId);
		
		List<ConcreteProduct> prods = new ArrayList<ConcreteProduct>();
		prods.add(prodId); // TODO: das hier ist offensichtlich.
		prodSearch.addProds(prods);
		
		return "redirect:/management";
	}

	@RequestMapping(value="management/addedArticle", method=RequestMethod.POST)
	public String addedArticle(@ModelAttribute("editArticleForm") @Valid EditArticleForm editForm,  @RequestParam("image") MultipartFile img, BindingResult result, ModelMap model){
		if (result.hasErrors()) {
			return "redirect:/management/addArticle/";
		}
	
		if (!img.isEmpty()) {
			try {
				byte[] bytes = img.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("filename"))); // TODO:
																													// generate
																													// filename
				stream.write(bytes);
				stream.close();
				System.out.println("success (yay) !!!"); // TODO: validation
			} catch (Exception e) {
				System.out.println("error (" + e.getMessage() + ") !!!");
			}
		} else {
			System.out.println("another error (file empty) !!!");
		}
		
		if(img.getOriginalFilename().isEmpty()){
			
			JOptionPane.showMessageDialog(null, "Bildpfad fehlt!");
			
		}
		
		if( editForm.getName().isEmpty()){
			JOptionPane.showMessageDialog(null, "Geben Sie bitte einen Artikelnamen an!");
		}
		
		if(editForm.getDescription().isEmpty()){
			JOptionPane.showMessageDialog(null,"Die Artikelbeschreibung fehlt!");
		}
		
		ConcreteProduct prodId= new ConcreteProduct(editForm.getName(),Money.of(editForm.getPrice(), "EUR"), editForm.getCategory(),editForm.getDescription(),"", img.getOriginalFilename());
		
		catalog.save(prodId);
		
		List<ConcreteProduct> prods = new ArrayList<ConcreteProduct>();
		prods.add(prodId); // TODO: das hier ist offensichtlich.
		prodSearch.addProds(prods);

		return "redirect:/management";
	
	}


	@RequestMapping("/management/deleteArticle/{prodId}")
	public String deleteArticle(@PathVariable("prodId") ConcreteProduct prod, Optional<UserAccount> userAccount, ModelMap model) {
		model.addAttribute("categories", prodSearch.getCagegories());
		model.addAttribute("concreteproduct", prod);
		model.addAttribute("price", prod.getPrice().getNumber());
		return "changecatalogdeleteitem";
	}
	
	@RequestMapping(value="/management/deletedArticle/{prodId}", method=RequestMethod.POST)
	public String deletedArticle(@PathVariable("prodId") ConcreteProduct prod){
		 
		System.out.println(prod);
		catalog.delete(prod);
	
		    
		
		return "redirect:/management";
		
		
	}


	public Inventory<InventoryItem> getInventory() {
		return inventory;
	}

	public static Quantity getNone() {
		return NONE;
	}
	


}
