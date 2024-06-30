package com.example.accounts.service.client;

import com.example.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

     @GetMapping(value = "/api/v1/cards/fetch", consumes = "application/json")
    ResponseEntity<CardDto> fetchCard(@RequestParam String mobileNumber);
}
