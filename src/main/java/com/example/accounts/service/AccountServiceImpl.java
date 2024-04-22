package com.example.accounts.service;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.constants.CustomerConstants;
import com.example.accounts.constants.SharedConstants;
import com.example.accounts.dto.AccountDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Account;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }


    private final Random random = new Random();


    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Optional<Customer> dbCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (dbCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(CustomerConstants.CUSTOMER_MOBILE_ALREADY_EXISTS);
        }

        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetch(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(SharedConstants.CUSTOMER, CustomerConstants.MOBILE_NUMBER, mobileNumber));


        List<Account> accounts = accountRepository.findAllByCustomerId(customer.getCustomerId());

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer);
        customerDto.setAccounts(AccountMapper.mapToAccountDtoList(accounts));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        if (customerDto == null) {
            return false;
        }

        if (customerDto.getAccounts() == null) {
            return false;
        }

        for (AccountDto accountDto : customerDto.getAccounts()) {
            Account account = accountRepository.findById(accountDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException(SharedConstants.ACCOUNT, AccountConstants.ACCOUNT_NUMBER, accountDto.getAccountNumber().toString()));

            Account newAccount = AccountMapper.mapToAccounts(accountDto, account);
            accountRepository.save(newAccount);
        }

        return true;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(SharedConstants.CUSTOMER, CustomerConstants.MOBILE_NUMBER, mobileNumber));

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        
        return true;
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + random.nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);

        return newAccount;
    }
}
