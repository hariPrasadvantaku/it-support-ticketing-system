package com.itsupport.ticketing.service;

import java.util.List;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketComment;
import com.itsupport.ticketing.entity.User;

public interface TicketCommentService {
	void addComment(Ticket ticket,String comment,User user);
	List<TicketComment> getCommentsByTicket(Ticket ticket);
}
