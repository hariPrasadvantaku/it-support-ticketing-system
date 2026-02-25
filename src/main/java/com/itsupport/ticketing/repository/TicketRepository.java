package com.itsupport.ticketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketStatus;
import com.itsupport.ticketing.entity.User;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
	List<Ticket> findByUser(User user);
	List<Ticket> findByStatus(TicketStatus status);
	List<Ticket> findByAssignedTo(User user);
	void deleteByUser(User user);
	void deleteByAssignedTo(User user);

}
