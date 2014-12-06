package factory.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@PreAuthorize("hasRole('ROLE_SALESMAN')")
public class Verkaeufercontroller {
	
	@RequestMapping(value="/bestellungen", method=RequestMethod.GET)
    public String Order(Model model) {
    	
	
        return "bestellungen";
    }
    
	

}
