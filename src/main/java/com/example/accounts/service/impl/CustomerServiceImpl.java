package com.example.accounts.service.impl;

import com.example.accounts.constants.CustomerConstants;
import com.example.accounts.constants.SharedConstants;
import com.example.accounts.dto.CardDto;
import com.example.accounts.dto.CustomerDetailsDto;
import com.example.accounts.dto.LoanDto;
import com.example.accounts.entity.Account;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.CustomerService;
import com.example.accounts.service.client.CardsFeignClient;
import com.example.accounts.service.client.LoansFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    @Autowired
    public CustomerServiceImpl(AccountRepository accountRepository,
                               CustomerRepository customerRepository,
                               CardsFeignClient cardsFeignClient,
                               LoansFeignClient loansFeignClient) {

        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
    }


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(SharedConstants.CUSTOMER, CustomerConstants.MOBILE_NUMBER, mobileNumber));


        List<Account> accounts = accountRepository.findAllByCustomerId(customer.getCustomerId());

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer);
        customerDetailsDto.setAccountsDtoList(AccountMapper.mapToAccountDtoList(accounts));

        ResponseEntity<LoanDto> loansDtoResponseEntity = loansFeignClient.fetchLoan(correlationId, mobileNumber);
        customerDetailsDto.setLoanDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCard(correlationId, mobileNumber);
        customerDetailsDto.setCardDto(cardDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
