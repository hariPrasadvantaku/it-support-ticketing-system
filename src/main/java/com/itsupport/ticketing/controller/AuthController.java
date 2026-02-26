
package com.itsupport.ticketing.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {
	   private final UserService userService;
	    private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;

	    public AuthController(UserService userService,
	                          UserRepository userRepository,
	                          PasswordEncoder passwordEncoder) {
	        this.userService = userService;
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	    }
	
	@GetMapping("/signup")
	public String showsSignUpPage(Model model) {
		
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute("user") User user,
	                     BindingResult result,
	                     Model model) {

	    if (result.hasErrors()) {
	        return "signup";
	    }

	    userService.registerUser(user);
	    return "redirect:/login";
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
	public String changePassword(@RequestParam String oldPassword,
	                              @RequestParam String newPassword,
	                              Authentication auth,
	                              RedirectAttributes ra) {

	    User user = userRepository.findByEmail(auth.getName());

	    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
	        ra.addFlashAttribute("error", "Old password is incorrect!");
	        return "redirect:/change-password";
	    }

	    user.setPassword(passwordEncoder.encode(newPassword));
	    userRepository.save(user);

	    ra.addFlashAttribute("success", "Password updated successfully!");
	    return "redirect:/change-password";
	}



	
}
