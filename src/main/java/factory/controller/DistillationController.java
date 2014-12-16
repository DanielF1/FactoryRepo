package factory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import factory.model.Production;
import factory.model.Still;

@Controller
public class DistillationController {
	
	@RequestMapping(value = "/distillation", method = RequestMethod.GET)
	public String still(Model model) 
	{
		model.addAttribute("stills", Production.getStills());

		return "distillation";
	}
	
	@RequestMapping(value = "/distillation/process/{index}", method = RequestMethod.POST)
	public String distillation(Model model, @PathVariable(value="index") int index, RedirectAttributes redirectAttrs) 
	{
		
		Still still = Production.getStills().get(index - 1);
		System.out.println(still);
		if((still.getStatus_one() == false) || (still.getStatus_two() == false))
		{
			model.addAttribute("error", "Destille " + index + " ist derzeit belegt!");
		}
		else
		{
			model.addAttribute("error2", "Destille " + index + " ist derzeit Koral!");
		}
		
		model.addAttribute("stills", Production.getStills());

		return "distillation";
	}
}
