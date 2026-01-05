package com.itsupport.ticketing.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketStatus;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.TicketService;

@Controller
@RequestMapping("/support/tickets")
public class SupportTicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;

    public SupportTicketController(TicketService ticketService,UserRepository userRepository) {
        this.ticketService = ticketService;
		this.userRepository = userRepository;
    }

    
    @GetMapping
    public String viewTickets(Model model) {
    	Authentication auth =
    	        SecurityContextHolder.getContext().getAuthentication();

    	    User supportUser =
    	        userRepository.findByEmail(auth.getName());

    	    List<Ticket> tickets =
    	        ticketService.getTicketsForSupport(supportUser);

    	    model.addAttribute("tickets", tickets);
    	    model.addAttribute("statuses", TicketStatus.values());

    	    return "support/tickets";
    }

   
    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long ticketId,
                               @RequestParam TicketStatus status) {

        ticketService.updateTicketStatus(ticketId, status);
        return "redirect:/support/tickets";
    }
}
