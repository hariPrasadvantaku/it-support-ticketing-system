package com.itsupport.ticketing.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusHistory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TicketStatus oldStatus;
	
	@Enumerated(EnumType.STRING)
	private TicketStatus newStatus;
	
	private LocalDateTime changedAt;
	
	@ManyToOne
	private Ticket ticket;
	
	@ManyToOne
	private User changedBy;
}
