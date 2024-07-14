package com.example.accounts.service.client;

import com.example.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {

    @Override
    public ResponseEntity<LoanDto> fetchLoan(String correlationId, String mobileNumber) {
        return null;
    }
}