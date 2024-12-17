package com.easecell.ease_cell.account.infra.repository;

import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.domain.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class AccountRepositoryMemory implements AccountRepository {
  private final ArrayList<Account> accounts;

  public AccountRepositoryMemory() {
    this.accounts = new ArrayList<>();
  }

  @Override
  public void save(Account account) {
    this.accounts.add(account);
  }

  @Override
  public Optional<Account> findByEmail(String email) {
    return this.accounts.stream().filter(account -> account.getEmail().equals(email)).findFirst();
  }

  @Override
  public Optional<Account> findByAccountId(String accountId) {

    return this.accounts.stream().filter(account -> account.getAccountId().toString().equals(accountId)).findFirst();
  }
}
