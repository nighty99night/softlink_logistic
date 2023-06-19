package ru.kalinichenko.softlink_logistic.twogis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ApiService {
    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public CompletableFuture<ResponseEntity<String>> getApiResponse(String url) {
        return CompletableFuture.supplyAsync(() -> restTemplate.getForEntity(url, String.class));
    }
}
