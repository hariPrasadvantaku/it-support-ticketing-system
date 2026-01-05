package com.itsupport.ticketing.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketStatus;
import com.itsupport.ticketing.service.TicketService;

@Controller
@RequestMapping("/support/tickets")
public class SupportTicketController {

    private final TicketService ticketService;

    public SupportTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    
    @GetMapping
    public String viewTickets(Model model) {
        List<Ticket> tickets = ticketService.getTicketsForSupport();
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
