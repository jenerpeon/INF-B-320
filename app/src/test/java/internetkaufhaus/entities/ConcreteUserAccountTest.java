package internetkaufhaus.entities;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.salespointframework.core.Currencies.EURO;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import internetkaufhaus.Application;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)


public class ConcreteUserAccountTest {

	private ConcreteUserAccount model1;
	private ConcreteUserAccount model2;

	@Autowired
	UserAccountManager u;
	
	@Before
	public void init() {
		List<UserAccount> recruits= new ArrayList<UserAccount>();

		model1 = new ConcreteUserAccount("Username1", "Username1", Role.of("ROLE_CUSTOMER"), u);
		model2 = new ConcreteUserAccount("test@mail.com", "Username2","Firstname", "Lastname", "Adress","ZipCode", "City", "Password", Role.of("ROLE_EMPLOYEE"),u,2,recruits);
		recruits.add(model1.getUserAccount());
	
		
	}
	
	@Test
	public void testGetterSetter(){
		Comment com=new Comment("Das hier ist ein Kommentar", 4, new Date(), "12");
		model1.addComment(com);
		assertTrue("Com hinzugefügt", model1.getComments().size()==1);
		assertTrue("Com get", model1.getComments().contains(com));
		
		Long id= 3L;
		model1.setId(id);
		assertTrue("id gesetzt", model1.getId()==id);
		
		assertTrue("Zipcode get", model2.getZipCode().equals("ZipCode"));
		model2.setZipCode("New");
		assertTrue("Zipcode geändert", model2.getZipCode().equals("New"));
		
		assertTrue("Role get", model2.getRole().equals(Role.of("ROLE_EMPLOYEE")));
		model2.setRole(Role.of("ROLE_CUSTOMER"));

		
		assertTrue("Email get", model2.getEmail().equals("test@mail.com"));
		model1.setEmail("test@mail.com");
		assertTrue("Email geändert", model1.getEmail().equals("test@mail.com"));
		
		assertTrue("Adress get", model2.getAddress().equals("Adress"));
		model1.setAddress("Adress1");
		assertTrue("Adresse geänder", model1.getAddress().equals("Adress1"));
		
		assertTrue("Credit get", model2.getCredits()==2);
		model2.setCredits(Money.of(4, EURO));
		assertTrue("Credit geändert", model2.getCredits()==4);
		
		assertTrue("Recruits get", model2.getRecruits().get(0)==model1.getUserAccount());
		
	
	}
}