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

import jakarta.servlet.http.HttpSession;

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
	public String login(@RequestParam String email, @RequestParam String password,Model model,HttpSession session) {
		User user = userService.login(email, password);
		
		if(user==null) {
			model.addAttribute("error","Invalid email or password");
			return "login";
		}
		
		session.setAttribute("loggedUser", user);
		
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
	
	@GetMapping("/logout")
	public String signout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
