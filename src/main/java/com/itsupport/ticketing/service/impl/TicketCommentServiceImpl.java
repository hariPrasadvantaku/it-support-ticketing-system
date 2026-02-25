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
public class TicketCommentServiceImpl implements TicketCommentService {

    private final TicketCommentRepository commentRepository;

    public TicketCommentServiceImpl(TicketCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<TicketComment> getCommentsForTicket(Ticket ticket, User viewer) {

        String role = viewer.getRole();

        if (role.equals("ROLE_USER") &&
            !ticket.getUser().getId().equals(viewer.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

 
        if (role.equals("ROLE_SUPPORT") &&
            (ticket.getAssignedTo() == null ||
             !ticket.getAssignedTo().getId().equals(viewer.getId()))) {
            throw new RuntimeException("Unauthorized access");
        }


        return commentRepository.findByTicketOrderByCreatedAtAsc(ticket);
    }

    @Override
    public void addComment(Ticket ticket, String content, User author) {

        TicketComment comment = new TicketComment();
        comment.setTicket(ticket);
        comment.setContent(content);
        comment.setUser(author);
        commentRepository.save(comment);
    }
}
