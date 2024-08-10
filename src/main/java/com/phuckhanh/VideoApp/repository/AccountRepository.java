package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);

    List<Account> findByChannel_NameContaining(String idChannel);

    @Transactional
    void deleteAccountByUsername(String username);
}
