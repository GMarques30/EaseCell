package com.easecell.ease_cell.account.application.repository;

import com.easecell.ease_cell.account.domain.entity.Account;

import java.util.Optional;

public interface AccountRepository {
  void save(Account account);
  Optional<Account> findByEmail(String email);
  Optional<Account> findByAccountId(String accountId);
}
