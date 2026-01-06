package com.itsupport.ticketing.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketComment;
import com.itsupport.ticketing.entity.User;
import com.itsupport.ticketing.repository.TicketCommentRepository;
import com.itsupport.ticketing.service.TicketCommentService;

@Service
public class TicketCommentServiceImpl implements TicketCommentService{
	
    private final TicketCommentRepository commentRepository;
    
    
    
	public TicketCommentServiceImpl(TicketCommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public void addComment(Ticket ticket, String comment, User user) {
		// TODO Auto-generated method stub
		TicketComment tc = new TicketComment();
		tc.setTicket(ticket);
		tc.setUser(user);
		tc.setComment(comment);
		tc.setCreatedAt(LocalDateTime.now());
		commentRepository.save(tc);
	}

	@Override
	public List<TicketComment> getCommentsByTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return commentRepository.findByTicketOrderByCreatedAtAsc(ticket);
	}

}
