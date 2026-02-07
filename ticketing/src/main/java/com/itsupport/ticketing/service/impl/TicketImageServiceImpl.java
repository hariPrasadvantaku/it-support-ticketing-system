package com.itsupport.ticketing.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itsupport.ticketing.entity.Ticket;
import com.itsupport.ticketing.entity.TicketImage;
import com.itsupport.ticketing.repository.TicketImageRepository;
import com.itsupport.ticketing.service.TicketImageService;
@Service
public class TicketImageServiceImpl implements TicketImageService {

    private final TicketImageRepository repo;

    public TicketImageServiceImpl(TicketImageRepository repo) {
        this.repo = repo;
    }

    @Override
    public void uploadImages(Ticket ticket, List<MultipartFile> files) {

        if (files == null || files.isEmpty()) return;

        for (MultipartFile file : files) {

            if (file.isEmpty()) continue;

            try {
                TicketImage img = new TicketImage();
                img.setTicket(ticket);
                img.setFileName(file.getOriginalFilename());
                img.setData(file.getBytes());
                img.setFilePath(null);       
                img.setType(file.getContentType());

                repo.save(img);

            } catch (Exception e) {
                throw new RuntimeException(
                    "Image upload failed for file: " + file.getOriginalFilename(), e);
            }
        }
    }

    @Override
    public List<TicketImage> getImages(Ticket ticket) {
        return repo.findByTicket(ticket);
    }
}
