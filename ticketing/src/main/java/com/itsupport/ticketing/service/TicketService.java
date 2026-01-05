package com.itsupport.ticketing.service;

import java.util.List;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketStatus;
import com.itsupport.ticketing.entity.User;

public interface TicketService {
	void createTicket(Ticket ticket,User user);
	List<Ticket> getTicketsForUser(User user);
	List<Ticket> getTicketsForSupport(User supportUser);
    void updateTicketStatus(Long ticketId, TicketStatus status);
    List<Ticket> getAllTickets();
    void assignTicket(Long ticketId, User supportUser);

}
