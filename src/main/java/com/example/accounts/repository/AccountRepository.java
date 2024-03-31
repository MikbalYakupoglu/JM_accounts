package com.example.accounts.repository;

import com.example.accounts.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByCustomerId(Long customerId);

    @Modifying
    @Transactional
    void deleteByCustomerId(Long customerId);
}
