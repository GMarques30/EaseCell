package com.easecell.ease_cell.account.application.usecase;

import com.easecell.ease_cell.account.application.dto.CreateAccountInput;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.domain.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountUseCase {
  private final AccountRepository accountRepository;

  public CreateAccountUseCase(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void execute(CreateAccountInput input) {
    var accountAlreadyExists = this.accountRepository.findByEmail(input.email());
    if(accountAlreadyExists.isPresent()) throw new IllegalArgumentException("Account already exists");
    var account = new Account(input.firstName(), input.lastName(), input.cpf(), input.birthDate(), input.phone(), input.email(), input.password());
    this.accountRepository.save(account);
  }
}
