package com.itsupport.ticketing.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.TicketStatusHistoryRepository;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.TicketCommentService;
import com.itsupport.ticketing.service.TicketImageService;
import com.itsupport.ticketing.service.TicketService;

@Controller
@RequestMapping("/admin/tickets")
public class AdminTicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;
    private final TicketCommentService commentService;
    private final TicketStatusHistoryRepository historyRepository;
    private final TicketImageService imageService;

    public AdminTicketController(
            TicketService ticketService,
            UserRepository userRepository,
            TicketCommentService commentService,
            TicketStatusHistoryRepository historyRepository,
            TicketImageService imageService) {

        this.ticketService = ticketService;
        this.userRepository = userRepository;
        this.commentService = commentService;
        this.historyRepository = historyRepository;
        this.imageService = imageService;
    }

    @GetMapping
    public String viewAllTickets(Model model) {

    	model.addAttribute("tickets", ticketService.getAllTickets());
        model.addAttribute("supports",
                userRepository.findByRole("ROLE_SUPPORT"));

        return "admin/tickets";
    }

    @PostMapping("/assign")
    public String assignTicket(@RequestParam Long ticketId,
                               @RequestParam Long supportId) {

        User support = userRepository.findById(supportId)
                .orElseThrow(() -> new RuntimeException("Support not found"));

        ticketService.assignTicket(ticketId, support);
        return "redirect:/admin/tickets";
    }

    @GetMapping("/{id}")
    public String viewTicket(@PathVariable Long id, Model model) {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        User admin = userRepository.findByEmail(auth.getName());
        Ticket ticket = ticketService.getTicketById(id);

        model.addAttribute("ticket", ticket);
        model.addAttribute("comments",
            commentService.getCommentsForTicket(ticket, admin));
        model.addAttribute("statusHistory",
            historyRepository.findByTicketOrderByChangedAtAsc(ticket));
        model.addAttribute("images", imageService.getImages(ticket));


        return "admin/ticket-details";
    }
}