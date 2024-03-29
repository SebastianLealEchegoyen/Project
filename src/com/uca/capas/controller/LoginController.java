package com.uca.capas.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.uca.capas.domain.User;
import com.uca.capas.dto.loginDTO;
import com.uca.capas.services.UserService;


@Controller
public class LoginController {
	
	@Autowired
	UserService Service;
	
	@RequestMapping(value= "/")
	public ModelAndView initMain() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("loginDTO", new loginDTO());
		mav.setViewName("Login");
		return mav;
	}
	
	@RequestMapping(value= "/Login" ,method= RequestMethod.POST)
	public ModelAndView Login(@Valid @ModelAttribute("loginDTO") loginDTO loginDTO ,BindingResult result) {
		ModelAndView mav = new ModelAndView();
       if(result.hasErrors() || Service.findUserandPass(loginDTO.getUser(), loginDTO.getClave())==null || Service.findUserandPass(loginDTO.getUser(), loginDTO.getClave()).getStateU().equals("Inactivo") || Service.findUserandPass(loginDTO.getUser(), loginDTO.getClave()).getSeccion()){
    	   mav.setViewName("Login");
       }	
       else {
    	     User user = Service.findUserandPass(loginDTO.getUser(), loginDTO.getClave());
    	     Service.UpdateS(user.getIdUser());
    	     if(user.getAccount().equals("admin")) {
    	    	   mav.addObject("user",user);
    	    	   mav.setViewName("HomeAdmin2");
    	     }
    	     else {
    	    	 mav.addObject("user",user);
    	    	 mav.setViewName("HomeCliente");
    	     }	   
    	   
       }
		return mav;
	}

}
