package com.itsupport.ticketing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.TicketService;

@Controller
@RequestMapping("/admin/tickets")
public class AdminTicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;

    public AdminTicketController(TicketService ticketService,
                                 UserRepository userRepository) {
        this.ticketService = ticketService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String viewTickets(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        model.addAttribute("supports",
                userRepository.findByRole("ROLE_SUPPORT"));
        return "admin/tickets";
    }

    @PostMapping("/assign")
    public String assignTicket(@RequestParam Long ticketId,
                               @RequestParam Long supportId) {

        User supportUser = userRepository.findById(supportId)
                .orElseThrow(() -> new RuntimeException("Support user not found"));

        ticketService.assignTicket(ticketId, supportUser);
        return "redirect:/admin/tickets";
    }
}
