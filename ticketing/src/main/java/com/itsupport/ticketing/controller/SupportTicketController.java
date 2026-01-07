package com.itsupport.ticketing.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketStatus;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.TicketStatusHistoryRepository;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.TicketCommentService;
import com.itsupport.ticketing.service.TicketService;

@Controller
@RequestMapping("/support/tickets")
public class SupportTicketController {

    private final TicketService ticketService;
    private final UserRepository userRepository;
    private final TicketCommentService commentService;
    private final TicketStatusHistoryRepository historyRepository;

    public SupportTicketController(
            TicketService ticketService,
            UserRepository userRepository,
            TicketCommentService commentService,
            TicketStatusHistoryRepository historyRepository) {

        this.ticketService = ticketService;
        this.userRepository = userRepository;
        this.commentService = commentService;
        this.historyRepository = historyRepository;
    }

    @GetMapping
    public String viewTickets(Model model) {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        User support = userRepository.findByEmail(auth.getName());
        List<Ticket> tickets = ticketService.getTicketsForSupport(support);

        Map<Long, List<TicketStatus>> statusMap = new HashMap<>();
        for (Ticket t : tickets) {
            statusMap.put(t.getId(),
                ticketService.getNextAllowedStatuses(t.getStatus()));
        }

        model.addAttribute("tickets", tickets);
        model.addAttribute("statusMap", statusMap);

        return "support/tickets";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long ticketId,
                               @RequestParam TicketStatus status,
                               RedirectAttributes redirectAttributes) {

        try {
            Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

            User support = userRepository.findByEmail(auth.getName());
            ticketService.changeStatus(ticketId, status, support);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/support/tickets";
    }

    @GetMapping("/{id}")
    public String viewTicketDetails(@PathVariable Long id, Model model) {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        User support = userRepository.findByEmail(auth.getName());
        Ticket ticket = ticketService.getTicketById(id);

        model.addAttribute("ticket", ticket);
        model.addAttribute("comments",
            commentService.getCommentsForTicket(ticket, support));
        model.addAttribute("statusHistory",
            historyRepository.findByTicketOrderByChangedAtAsc(ticket));

        return "support/ticket-details";
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam("comment") String comment) {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        User support = userRepository.findByEmail(auth.getName());
        Ticket ticket = ticketService.getTicketById(id);

        commentService.addComment(ticket, comment, support);
        return "redirect:/support/tickets/" + id;
    }
}
