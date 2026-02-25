package com.itsupport.ticketing.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketImage;

public interface TicketImageService {
	void uploadImages(Ticket ticket,List<MultipartFile> files);
	List<TicketImage> getImages(Ticket ticket);
}
