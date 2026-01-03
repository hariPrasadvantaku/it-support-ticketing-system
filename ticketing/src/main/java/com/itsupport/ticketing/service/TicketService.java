package com.itsupport.ticketing.service;

import java.util.List;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.User;

public interface TicketService {
	void createTicket(Ticket ticket,User user);
	List<Ticket> getTicketForUser(User user);
}
