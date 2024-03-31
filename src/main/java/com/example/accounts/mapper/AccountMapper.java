package com.example.accounts.mapper;

import com.example.accounts.dto.AccountDto;
import com.example.accounts.entity.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {
    private AccountMapper() {
    }

    public static AccountDto mapToAccountDto(Account accounts) {
        AccountDto accountsDto = new AccountDto();
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static List<AccountDto> mapToAccountDtoList(List<Account> accounts) {
        List<AccountDto> accountDtos = new ArrayList<>();
        for(Account account : accounts){
            AccountDto accountsDto = new AccountDto();
            accountsDto.setAccountNumber(account.getAccountNumber());
            accountsDto.setAccountType(account.getAccountType());
            accountsDto.setBranchAddress(account.getBranchAddress());

            accountDtos.add(accountsDto);
        }
        return accountDtos;
    }

    public static AccountDto mapToAccountDto(Account accounts, AccountDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }


    public static Account mapToAccounts(AccountDto accountsDto) {
        Account accounts = new Account();
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

    public static Account mapToAccounts(AccountDto accountsDto, Account accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
