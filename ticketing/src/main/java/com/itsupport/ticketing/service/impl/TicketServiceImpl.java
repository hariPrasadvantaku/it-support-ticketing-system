package com.itsupport.ticketing.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketStatus;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.TicketRepository;
import com.itsupport.ticketing.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	private final TicketRepository ticketRepository;

	public TicketServiceImpl(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@Override
	public void createTicket(Ticket ticket, User user) {
		// TODO Auto-generated method stub
		ticket.setUser(user);
		ticket.setStatus(TicketStatus.OPEN);
		ticket.setCreatedAt(LocalDateTime.now());
		ticketRepository.save(ticket);
	}

	@Override
	public List<Ticket> getTicketsForUser(User user) {
		// TODO Auto-generated method stub

		return ticketRepository.findByUser(user);
	}
	

	@Override
	public List<Ticket> getTicketsForSupport() {
		return ticketRepository.findAll();
	}

	@Override
	public void updateTicketStatus(Long ticketId, TicketStatus status) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

		ticket.setStatus(status);
		ticketRepository.save(ticket);
	}

}
