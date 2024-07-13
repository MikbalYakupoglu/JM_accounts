package com.example.accounts.controller;

import com.example.accounts.dto.CustomerDetailsDto;
import com.example.accounts.service.CustomerService;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "/customers", produces = MediaType.APPLICATION_JSON)
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("nakoual-correlation-id") String correlationId,
     @RequestParam @Pattern(regexp = "(^|\\d{10})", message = "MobileNumber must be 10 digits")
                                                            String mobileNumber) {


        logger.info("nakoual-correlation-id found : {}", correlationId);
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity.ok(customerDetailsDto);
    }
}
