package com.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ShortUrlRequestDTO(

        @NotBlank(message = "URL cannot be empty")
        @URL(message = "URL must be valid")
        String longUrl
) {}