package com.example.traiviu_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PublicUrlService {

    @Value("${app.public-base-url}")
    private String publicBaseUrl;

    public String buildPublicUrl(String path) {
        if (path == null || path.isBlank()) {
            return null;
        }

        if (path.startsWith("http://") || path.startsWith("https://")) {
            return path;
        }

        String base = publicBaseUrl.endsWith("/")
                ? publicBaseUrl.substring(0, publicBaseUrl.length() - 1)
                : publicBaseUrl;

        String normalizedPath = path.startsWith("/") ? path : "/" + path;

        return base + normalizedPath;
    }
}