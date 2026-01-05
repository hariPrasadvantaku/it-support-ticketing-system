package com.itsupport.ticketing.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.TicketService;

@Controller
@RequestMapping("/user/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;

    public TicketController(TicketService ticketService, UserRepository userRepository) {
        this.ticketService = ticketService;
        this.userRepository = userRepository;
    }

   
    @GetMapping
    public String listTickets(Model model) {
        User user = getLoggedUser();
        model.addAttribute("tickets", ticketService.getTicketsForUser(user));
        return "user/tickets";
    }

   
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "user/create-ticket";
    }

    
    @PostMapping
    public String createTicket(@ModelAttribute Ticket ticket) {
        User user = getLoggedUser();
        ticketService.createTicket(ticket, user);
        return "redirect:/user/tickets";
    }

    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName());
    }
}
