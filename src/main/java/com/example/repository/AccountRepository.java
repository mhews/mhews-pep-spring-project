package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findAccountByUsername(String username);

    Account findAccountByAccountId(int accountId);

    Account findAccountByUsernameAndPassword(String username, String password);
}
