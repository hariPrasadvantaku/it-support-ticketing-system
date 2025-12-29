package com.itsupport.ticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password,Model model) {
		System.out.println("Login Attempt "+email);
		User user = userService.login(email, password);
		
		if(user==null) {
			model.addAttribute("error","Invalid email or password");
			return "login";
		}
		
		String role = user.getRole();
		if("ADMIN".equals(role)) {
			return "redirect:/admin/dashboard";
		}
		else if("SUPPORT".equals(role)) {
			return "redirect:/support/dashboard";
		}
		else {
			return "redirect:/user/dashboard";
		}
	}
}
