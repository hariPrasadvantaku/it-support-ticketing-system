package com.itsupport.ticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.service.UserService;

@Controller
public class AuthController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String showsSignUpPage(Model model) {
		
		
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute("user") User user,Model model) {
		
		System.out.println("SIGNUP CONTROLLER HIT");
	    System.out.println("Username: " + user.getUsername());
	    System.out.println("Email: " + user.getEmail());
	    System.out.println("Password: " + user.getPassword());
		
		try {
			userService.registerUser(user);
			return "redirect:/login";
		}
		catch(Exception e) {
			model.addAttribute("error",e.getMessage());
			return "signup";
		}
	}
}
