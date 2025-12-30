package com.itsupport.ticketing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import com.itsupport.ticketing.entity.User;

@Controller
public class DashboardController {
	
	@GetMapping("/user/dashboard")
	public String userDashboard(HttpSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		if(loggedUser==null) {
			return "redirect:/login";
		}
		
		if (!"USER".equalsIgnoreCase(loggedUser.getRole().trim())) {
		    return "redirect:/access-denied";
		}

		return "user/dashboard";
	}
	
	@GetMapping("/support/dashboard")
	public String supportDashboard(HttpSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		if(loggedUser==null) {
			return "redirect:/login";
		}
		if (!"SUPPORT".equalsIgnoreCase(loggedUser.getRole().trim())) {
		    return "redirect:/access-denied";
		}

		return "support/dashboard";
	}
	
	@GetMapping("/admin/dashboard")
	public String adminDashboard(HttpSession session) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		if(loggedUser==null) {
			return "redirect:/login";
		}
		if (!"ADMIN".equalsIgnoreCase(loggedUser.getRole().trim())) {
		    return "redirect:/access-denied";
		}

		return "admin/dashboard";
	}
}
