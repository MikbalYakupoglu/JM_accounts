package com.example.accounts.service;

import com.example.accounts.dto.CustomerDetailsDto;

public interface CustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
