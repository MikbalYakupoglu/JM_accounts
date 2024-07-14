package com.example.accounts.service.client;

import com.example.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

     @GetMapping(value = "/cards/fetch", consumes = "application/json")
    ResponseEntity<CardDto> fetchCard(@RequestHeader("nakoual-correlation-id") String correlationId, @RequestParam String mobileNumber);
}
