package com.itsupport.ticketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketComment;

public interface TicketCommentRepository extends JpaRepository<TicketComment,Long>{
	List<TicketComment> findByTicketOrderByCreatedAtAsc(Ticket ticket);
}
