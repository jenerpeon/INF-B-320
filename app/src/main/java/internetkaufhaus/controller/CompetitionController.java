package internetkaufhaus.controller;

import org.salespointframework.useraccount.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import internetkaufhaus.model.Competition;
import internetkaufhaus.repositories.ConcreteUserAccountRepository;

@Controller
public class CompetitionController {
	
	private ConcreteUserAccountRepository repo;
	@Autowired
	public CompetitionController(ConcreteUserAccountRepository repo)
	{
		this.repo = repo;
	}
	@RequestMapping(value="/admin/lottery")
	public String competition(ModelMap model)
	{
		
		return "competition";
	}
	@RequestMapping(value="/admin/competitionButton")
	public String getWinners()
	{
		Competition com = new Competition(repo.findAll());
		com.getWinners();
		return "redirect:/admin/lottery";
	}

}
