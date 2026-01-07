package com.itsupport.ticketing.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.TicketStatusHistoryRepository;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.TicketCommentService;
import com.itsupport.ticketing.service.TicketImageService;
import com.itsupport.ticketing.service.TicketService;

@Controller
@RequestMapping("/user/tickets")
public class UserTicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;
    private final TicketCommentService commentService;
    private final TicketStatusHistoryRepository historyRepository;
    private final TicketImageService imageService;
    public UserTicketController(
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
    
    @GetMapping("/create")
    public String showCreateTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "user/create-ticket";
    }
   
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

    @GetMapping("/{id}")
    public String viewTicketDetails(@PathVariable Long id, Model model) {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(auth.getName());
        Ticket ticket = ticketService.getTicketById(id);

        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        model.addAttribute("ticket", ticket);
        model.addAttribute("comments",
            commentService.getCommentsForTicket(ticket, user));
        model.addAttribute("statusHistory",
            historyRepository.findByTicketOrderByChangedAtAsc(ticket));
        model.addAttribute("images", imageService.getImages(ticket));


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
    
    @PostMapping("/create")
    public String createTicket(
            @ModelAttribute Ticket ticket,
            @RequestParam(value = "images", required = false)
            List<MultipartFile> images) {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(auth.getName());

        ticketService.createTicket(ticket, user);

        if (images != null && !images.isEmpty()) {
            imageService.uploadImages(ticket, images);
        }

        return "redirect:/user/tickets";
    }



}
