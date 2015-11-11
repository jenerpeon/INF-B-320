package internetkaufhaus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
	
	public TestController()
	{
		
	}
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String land()
	{
		return "test";
	}
	
	@RequestMapping(value = "/testValue", method = RequestMethod.POST)
	public String testfoo(@RequestParam("testName") String testValue)
	{
		String test = testValue;
		return "redirect:"+test;
	}

}
