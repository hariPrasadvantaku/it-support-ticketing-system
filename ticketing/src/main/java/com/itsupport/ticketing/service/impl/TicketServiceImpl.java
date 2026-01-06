package com.itsupport.ticketing.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketStatus;
import com.itsupport.ticketing.entity.TicketStatusHistory;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.TicketRepository;
import com.itsupport.ticketing.repository.TicketStatusHistoryRepository;
import com.itsupport.ticketing.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	private final TicketRepository ticketRepository;
	private final TicketStatusHistoryRepository historyRepository;
	public TicketServiceImpl(TicketRepository ticketRepository,TicketStatusHistoryRepository historyRepository) {
		this.ticketRepository = ticketRepository;
		this.historyRepository = historyRepository;
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
	public List<Ticket> getTicketsForSupport(User supportUser) {
		return ticketRepository.findByAssignedTo(supportUser);
	}
	
	@Override
	public Ticket getTicketById(Long id) {
	    return ticketRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Ticket not found"));
	}

	@Override
	public void updateTicketStatus(Long ticketId, TicketStatus status) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

		ticket.setStatus(status);
		ticketRepository.save(ticket);
	}
	
	@Override
	public List<Ticket> getAllTickets() {
	    return ticketRepository.findAll();
	}

	@Override
	public void assignTicket(Long ticketId, User supportUser) {
	    Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new RuntimeException("Ticket not found"));

	    ticket.setAssignedTo(supportUser);
	    ticketRepository.save(ticket);
	}
	
	@Override
	public void changeStatus(Long ticketId,
	                         TicketStatus newStatus,
	                         User changedBy) {

	    Ticket ticket = ticketRepository.findById(ticketId)
	        .orElseThrow(() -> new RuntimeException("Ticket not found"));

	    TicketStatus currentStatus = ticket.getStatus();

	    if (!isValidTransition(currentStatus, newStatus)) {
	        throw new RuntimeException(
	            "Invalid status transition: " + currentStatus + " → " + newStatus);
	    }

	    TicketStatusHistory history = new TicketStatusHistory();
	    history.setTicket(ticket);
	    history.setOldStatus(currentStatus);
	    history.setNewStatus(newStatus);
	    history.setChangedAt(LocalDateTime.now());
	    history.setChangedBy(changedBy);

	    historyRepository.save(history);

	    ticket.setStatus(newStatus);
	    ticketRepository.save(ticket);
	}
	
	@Override
	public List<TicketStatus> getNextAllowedStatuses(TicketStatus currentStatus) {

	    return switch (currentStatus) {
	        case OPEN -> List.of(TicketStatus.IN_PROGRESS);
	        case IN_PROGRESS -> List.of(TicketStatus.RESOLVED);
	        case RESOLVED -> List.of(TicketStatus.CLOSED);
	        case CLOSED -> List.of();
	    };
	}

	
	private boolean isValidTransition(TicketStatus from, TicketStatus to) {

	    return switch (from) {
	        case OPEN -> to == TicketStatus.IN_PROGRESS;
	        case IN_PROGRESS -> to == TicketStatus.RESOLVED;
	        case RESOLVED -> to == TicketStatus.CLOSED;
	        case CLOSED -> false;
	    };
	}
	
	

}
