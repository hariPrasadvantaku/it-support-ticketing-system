package com.itsupport.ticketing.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itsupport.ticketing.entity.TicketImage;
import com.itsupport.ticketing.repository.TicketImageRepository;

@RestController
public class TicketImageController {

    private final TicketImageRepository repo;

    public TicketImageController(TicketImageRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> previewImage(@PathVariable Long id) {

        TicketImage img = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .contentType(MediaType.IMAGE_JPEG) // works for png/jpg
                .body(img.getData());
    }
}	