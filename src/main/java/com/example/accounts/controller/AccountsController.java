package com.example.accounts.controller;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.constants.ServerConstants;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createDto(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.ACCOUNT_CREATED_SUCCESSFULLY));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp = "(^|\\d{10})", message = "MobileNumber must be 10 digits")
                                                               String mobileNumber) {
        CustomerDto customerDto = accountService.fetch(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isSuccess = accountService.updateAccount(customerDto);

        if (isSuccess) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.ACCOUNT_UPDATED_SUCCESSFULLY));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(ServerConstants.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "(^|\\d{10})", message = "MobileNumber must be 10 digits")
                                                         String mobileNumber) {
        boolean isSuccess = accountService.deleteAccount(mobileNumber);

        if (isSuccess) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.ACCOUNT_DELETED_SUCCESSFULLY));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(ServerConstants.INTERNAL_SERVER_ERROR));
    }
}
