package com.example.accounts.service.client;

import com.example.accounts.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value = "/api/v1/loans/fetch", consumes = "application/json")
    ResponseEntity<LoanDto> fetchLoan(@RequestParam String mobileNumber);
}