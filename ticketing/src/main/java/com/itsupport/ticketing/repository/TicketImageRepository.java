package com.itsupport.ticketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketImage;

public interface TicketImageRepository extends JpaRepository<TicketImage,Long>{
	List<TicketImage> findByTicket(Ticket ticket);
}
