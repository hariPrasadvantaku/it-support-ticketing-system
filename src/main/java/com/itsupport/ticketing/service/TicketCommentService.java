package com.itsupport.ticketing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketComment;
import com.itsupport.ticketing.entity.User;

@Service
public interface TicketCommentService {
	List<TicketComment> getCommentsForTicket(Ticket ticket, User viewer);

    void addComment(Ticket ticket, String content, User author);
}
