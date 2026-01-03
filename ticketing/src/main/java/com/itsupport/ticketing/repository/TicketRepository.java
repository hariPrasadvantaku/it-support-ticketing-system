package com.itsupport.ticketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.User;

public interface TicketRepository extends JpaRepository {
	List<Ticket> findByUser(User user);
}
