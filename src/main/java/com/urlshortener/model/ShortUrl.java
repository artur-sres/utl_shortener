package com.urlshortener.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String longUrl;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @Column(nullable = false)
    private Long accessCount = 0L;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getLongUrl() {return longUrl;}
    public void setLongUrl(String longUrl) {this.longUrl = longUrl;}

    public String getCode() {return code;}
    public void setCode(String code) {this.code = code;}

    public Long getAccessCount() {return accessCount;}
    public void setAccessCount(Long accessCount) {this.accessCount = accessCount;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}

