package com.itsupport.ticketing.controller;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.TicketRepository;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TicketRepository ticketRepository;
    public AdminUserController(UserRepository userRepository,UserService userService,PasswordEncoder passwordEncoder,TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.ticketRepository=ticketRepository;
    }

    @GetMapping("/create")
    public String showCreateUserPage() {
        return "admin/create-user";
    }


    @PostMapping("/create")
    public String createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // IMPORTANT
        user.setRole(role);

        userRepository.save(user);

        return "redirect:/admin/users";
    }

    
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepository.findByActiveTrue(); // CORRECT
        model.addAttribute("users", users);
        return "admin/users";
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

 
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam(required = false) String newPassword) {

        userService.updateUser(user, newPassword);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) return "redirect:/admin/users?error";

        if ("ROLE_ADMIN".equals(user.getRole()))
            return "redirect:/admin/users?cannotdelete";

        user.setActive(false);
        userRepository.save(user);

        return "redirect:/admin/users?deactivated";
    }




}
