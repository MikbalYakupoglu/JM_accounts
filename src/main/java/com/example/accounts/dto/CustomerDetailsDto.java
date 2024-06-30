package com.example.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDetailsDto {

    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of customer name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email should be a valid email")
    private String email;

    @Pattern(regexp = "(^|\\d{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private List<AccountDto> accountsDtoList;
    private CardDto cardDto;
    private LoanDto loanDto;

}
