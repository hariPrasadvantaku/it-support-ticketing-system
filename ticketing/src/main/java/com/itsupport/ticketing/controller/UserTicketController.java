package com.itsupport.ticketing.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketComment;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.TicketCommentService;
import com.itsupport.ticketing.service.TicketService;

@Controller
@RequestMapping("/user/tickets")
public class UserTicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;
    private final TicketCommentService commentService;

    public UserTicketController(
            TicketService ticketService,
            UserRepository userRepository,
            TicketCommentService commentService) {

        this.ticketService = ticketService;
        this.userRepository = userRepository;
        this.commentService = commentService;
    }

    // 1️⃣ View all tickets of logged-in user
    @GetMapping
    public String viewUserTickets(Model model) {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(auth.getName());

        List<Ticket> tickets =
            ticketService.getTicketsForUser(user);

        model.addAttribute("tickets", tickets);

        return "user/tickets";
    }

    // 2️⃣ View single ticket + comments
    @GetMapping("/{id}")
    public String viewTicket(@PathVariable Long id, Model model) {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(auth.getName());
        Ticket ticket = ticketService.getTicketById(id);

        List<TicketComment> comments =
            commentService.getCommentsForTicket(ticket, user);

        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", comments);

        return "user/ticket-details";
    }

    @PostMapping("/{id}/comment")
    public String addComment(
            @PathVariable Long id,
            @RequestParam("comment") String commentText) {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(auth.getName());
        Ticket ticket = ticketService.getTicketById(id);

        commentService.addComment(ticket, commentText, user);

        return "redirect:/user/tickets/" + id;
    }

}
