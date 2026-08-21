package com.urlshortener.controller;

import com.urlshortener.dto.ShortUrlRequestDTO;
import com.urlshortener.dto.ShortUrlResponseDTO;
import com.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ShortUrlController {

    private final ShortUrlService service;

    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }

    @PostMapping("/api/urls")
    public ResponseEntity<ShortUrlResponseDTO> create(
            @Valid @RequestBody ShortUrlRequestDTO request) {

        ShortUrlResponseDTO response = service.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/api/urls/{code}")
    public ResponseEntity<ShortUrlResponseDTO> findByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(service.findByCode(code));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(
            @PathVariable String code) {

        String longUrl = service.getLongUrlAndIncrementAccess(code);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
}