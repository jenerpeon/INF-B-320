package internetkaufhaus.controller;

import java.util.Iterator;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import internetkaufhaus.model.ConcreteUserAccountManager;

@Controller
public class TestController {
	private ConcreteUserAccountManager manager;
	
	@Autowired
	public TestController(ConcreteUserAccountManager manager)
	{
		this.manager = manager;
	}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String land()
	{
		manager.find();
		return "redirect:index";
	}
	
	@RequestMapping(value = "/testValue", method = RequestMethod.POST)
	public String testfoo(@RequestParam("testName") String testValue)
	{
		String test = testValue;
		return "redirect:"+test;
	}

}
