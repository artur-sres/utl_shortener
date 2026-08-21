package com.urlshortener.service;

import com.urlshortener.dto.ShortUrlRequestDTO;
import com.urlshortener.dto.ShortUrlResponseDTO;
import com.urlshortener.exception.ShortUrlNotFoundException;
import com.urlshortener.model.ShortUrl;
import com.urlshortener.repository.ShortUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShortUrlServiceTest {

    private ShortUrlRepository repository;
    private ShortUrlService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ShortUrlRepository.class);
        service = new ShortUrlService(repository);
    }

    @Test
    void shouldCreateShortUrl() {
        ShortUrlRequestDTO request = new ShortUrlRequestDTO("https://github.com");

        when(repository.existsByCode(any())).thenReturn(false);

        when(repository.save(any(ShortUrl.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ShortUrlResponseDTO response = service.create(request);

        assertNotNull(response);
        assertEquals("https://github.com", response.longUrl());
        assertNotNull(response.code());
        assertEquals(0L, response.accessCount());

        verify(repository).save(any(ShortUrl.class));
    }

    @Test
    void shouldFindShortUrlByCode() {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setLongUrl("https://github.com");
        shortUrl.setCode("abc123");
        shortUrl.setAccessCount(0L);
        shortUrl.setCreatedAt(LocalDateTime.now());

        when(repository.findByCode("abc123"))
                .thenReturn(Optional.of(shortUrl));

        ShortUrlResponseDTO response = service.findByCode("abc123");

        assertEquals("abc123", response.code());
        assertEquals("https://github.com", response.longUrl());
    }

    @Test
    void shouldThrowExceptionWhenCodeDoesNotExist() {
        when(repository.findByCode("invalid"))
                .thenReturn(Optional.empty());

        assertThrows(
                ShortUrlNotFoundException.class,
                () -> service.findByCode("invalid")
        );
    }

    @Test
    void shouldIncrementAccessCount() {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setLongUrl("https://github.com");
        shortUrl.setCode("abc123");
        shortUrl.setAccessCount(0L);

        when(repository.findByCode("abc123"))
                .thenReturn(Optional.of(shortUrl));

        String longUrl = service.getLongUrlAndIncrementAccess("abc123");

        assertEquals("https://github.com", longUrl);
        assertEquals(1L, shortUrl.getAccessCount());

        verify(repository).save(shortUrl);
    }
}