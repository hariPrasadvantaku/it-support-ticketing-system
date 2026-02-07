package com.itsupport.ticketing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserRepository userRepository;

    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        user.setPassword(password);
        user.setRole(role);

        userRepository.save(user);

        return "redirect:/admin/dashboard";
    }
}
