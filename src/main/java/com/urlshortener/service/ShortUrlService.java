package com.urlshortener.service;

import com.urlshortener.dto.ShortUrlRequestDTO;
import com.urlshortener.dto.ShortUrlResponseDTO;
import com.urlshortener.exception.ShortUrlNotFoundException;
import com.urlshortener.model.ShortUrl;
import com.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ShortUrlService {

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int CODE_LENGTH = 6;

    private final ShortUrlRepository repository;
    private final SecureRandom random = new SecureRandom();

    public ShortUrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public ShortUrlResponseDTO create(ShortUrlRequestDTO request) {
        ShortUrl shortUrl = new ShortUrl();

        shortUrl.setLongUrl(request.longUrl());
        shortUrl.setCode(generateUniqueCode());
        shortUrl.setAccessCount(0L);

        ShortUrl savedUrl = repository.save(shortUrl);

        return toResponse(savedUrl);
    }

    public ShortUrlResponseDTO findByCode(String code) {
        ShortUrl shortUrl = repository.findByCode(code)
                .orElseThrow(() -> new ShortUrlNotFoundException(code));

        return toResponse(shortUrl);
    }

    public String getLongUrlAndIncrementAccess(String code) {
        ShortUrl shortUrl = repository.findByCode(code)
                .orElseThrow(() -> new ShortUrlNotFoundException(code));

        shortUrl.setAccessCount(shortUrl.getAccessCount() + 1);

        repository.save(shortUrl);

        return shortUrl.getLongUrl();
    }

    private String generateUniqueCode() {
        String code;

        do {
            code = generateCode();
        } while (repository.existsByCode(code));

        return code;
    }

    private String generateCode() {
        StringBuilder builder = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(index));
        }

        return builder.toString();
    }

    private ShortUrlResponseDTO toResponse(ShortUrl shortUrl) {
        return new ShortUrlResponseDTO(
                shortUrl.getCode(),
                shortUrl.getLongUrl(),
                shortUrl.getAccessCount(),
                shortUrl.getCreatedAt()
        );
    }
}