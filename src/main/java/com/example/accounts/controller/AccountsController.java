package com.example.accounts.controller;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.constants.ServerConstants;
import com.example.accounts.dto.AccountContactInfoDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ErrorResponseDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.AccountService;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CRUD REST API for Account Microservice")
@RestController
@RequestMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    private final AccountService accountService;
    private final Environment environment;
    private final AccountContactInfoDto accountContactInfoDto;

    @Autowired
    public AccountsController(AccountService accountService, Environment environment, AccountContactInfoDto accountContactInfoDto) {
        this.accountService = accountService;
        this.environment = environment;
        this.accountContactInfoDto = accountContactInfoDto;
    }

    private static final Logger logger = LogManager.getLogger(AccountsController.class);

    @Value("${build.version}")
    private String buildVersion;
    


    @Operation(
            summary = "Create Account REST API"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
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

    @Operation(
            summary = "Update Account REST API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
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

    @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("java.version"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }

    private ResponseEntity<String> getBuildInfoFallback(Throwable throwable){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(throwable.getLocalizedMessage() != null && !throwable.getLocalizedMessage().isEmpty()
                        ? throwable.getLocalizedMessage()
                        : "Build info is not available at the moment. Please try again later.");
    }
}


