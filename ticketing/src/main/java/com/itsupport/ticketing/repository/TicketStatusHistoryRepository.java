package com.itsupport.ticketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketStatusHistory;

public interface TicketStatusHistoryRepository extends JpaRepository<TicketStatusHistory,Long>{
	List<TicketStatusHistory> findByTicketOrderByChangedAtAsc(Ticket ticket);
}
