package com.urlshortener.dto;

import java.time.LocalDateTime;

public record ShortUrlResponseDTO(
        String code,
        String longUrl,
        Long accessCount,
        LocalDateTime createdAt
) {}