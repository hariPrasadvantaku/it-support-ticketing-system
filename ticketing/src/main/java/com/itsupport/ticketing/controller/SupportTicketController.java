package com.itsupport.ticketing.controller;

import java.util.List;

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
import com.itsupport.ticketing.entity.TicketComment;
import com.itsupport.ticketing.entity.TicketStatus;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.UserRepository;
import com.itsupport.ticketing.service.TicketCommentService;
import com.itsupport.ticketing.service.TicketService;

@Controller
@RequestMapping("/support/tickets")
public class SupportTicketController {

	private final TicketService ticketService;
	private final UserRepository userRepository;
	private final TicketCommentService commentService;

	public SupportTicketController(TicketService ticketService, UserRepository userRepository,
			TicketCommentService commentService) {

		this.ticketService = ticketService;
		this.userRepository = userRepository;
		this.commentService = commentService;
	}

	@GetMapping
	public String viewTickets(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User supportUser = userRepository.findByEmail(auth.getName());

		List<Ticket> tickets = ticketService.getTicketsForSupport(supportUser);

		model.addAttribute("tickets", tickets);
		model.addAttribute("statuses", TicketStatus.values());

		return "support/tickets";
	}

	@PostMapping("/update-status")
	public String updateStatus(@RequestParam Long ticketId, @RequestParam TicketStatus status) {

		ticketService.updateTicketStatus(ticketId, status);
		return "redirect:/support/tickets";
	}

	@GetMapping("/{id}")
	public String viewTicketDetails(@PathVariable Long id, Model model) {

		Ticket ticket = ticketService.getTicketById(id);
		List<TicketComment> comments = commentService.getCommentsByTicket(ticket);

		model.addAttribute("ticket", ticket);
		model.addAttribute("comments", comments);

		return "support/ticket-details";
	}

	@PostMapping("/{id}/comment")
	public String addComment(@PathVariable Long id, @RequestParam String comment) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userRepository.findByEmail(auth.getName());
		Ticket ticket = ticketService.getTicketById(id);

		commentService.addComment(ticket, comment, user);

		return "redirect:/support/tickets/" + id;
	}

}
