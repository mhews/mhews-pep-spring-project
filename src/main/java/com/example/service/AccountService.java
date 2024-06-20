package com.example.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateAccountException;
import com.example.exception.InvalidFormatException;
import com.example.repository.AccountRepository;

@Transactional
@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) throws DuplicateAccountException, InvalidFormatException{
        if (account.getPassword().length() < 4 || account.getUsername().length() == 0){
            throw new InvalidFormatException();
        }
        if (accountRepository.findAccountByUsername(account.getUsername()) != null){
            throw new DuplicateAccountException();
        }
        return accountRepository.save(account);
    }

    public Account login(Account account){
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
