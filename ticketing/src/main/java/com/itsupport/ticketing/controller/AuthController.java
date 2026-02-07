
package com.itsupport.ticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.UserService;

@Controller
public class AuthController {
	@Autowired
	private UserService userService;
	private UserRepository userRepository;
	
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
	
	@GetMapping("/change-password")
	public String changePasswordPage() {
	    return "change-password";
	}

	@PostMapping("/change-password")
	public String changePassword(
	        @RequestParam String oldPassword,
	        @RequestParam String newPassword) {

	    Authentication auth =
	        SecurityContextHolder.getContext().getAuthentication();

	    User user = userRepository.findByEmail(auth.getName());

	    if (!user.getPassword().equals(oldPassword)) {
	        throw new RuntimeException("Wrong old password");
	    }

	    user.setPassword(newPassword);
	    userRepository.save(user);

	    return "redirect:/dashboard";
	}

	
}
