package com.itsupport.ticketing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


@Controller
public class DashboardController {
	
	@GetMapping("/user/dashboard")
	public String userDashboard(HttpSession session) {
		
		if(session.getAttribute("loggedUser")==null) {
			return "redirect:/login";
		}
		return "user/dashboard";
	}
	
	@GetMapping("/support/dashboard")
	public String supportDashboard(HttpSession session) {
		if(session.getAttribute("loggedUser")==null) {
			return "redirect:/login";
		}
		return "support/dashboard";
	}
	
	@GetMapping("/admin/dashboard")
	public String adminDashboard(HttpSession session) {
		if(session.getAttribute("loggedUser")==null) {
			return "redirect:/login";
		}
		return "admin/dashboard";
	}
}
