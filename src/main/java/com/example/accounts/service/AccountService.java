package com.example.accounts.service;


import com.example.accounts.dto.CustomerDto;

public interface AccountService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);


    /**
     * @param mobileNumber - Input Mobile Number
     * @return AccountDetails based on a given number.
     */
    CustomerDto fetch(String mobileNumber);


    /**
     * @param customerDto - CustomerDto Object
     * @return Is update successful or not
     */
    boolean updateAccount(CustomerDto customerDto);


    /**
     * @param mobileNumber - Input Mobile Number
     * @return Is delete successful or not
     */
    boolean deleteAccount(String mobileNumber);
}
