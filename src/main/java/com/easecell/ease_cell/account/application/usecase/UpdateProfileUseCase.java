package com.easecell.ease_cell.account.application.usecase;

import com.easecell.ease_cell.account.application.dto.UpdateProfileInput;
import com.easecell.ease_cell.account.application.dto.UpdateProfileOutput;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.domain.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class UpdateProfileUseCase {
  private final AccountRepository accountRepository;

  public UpdateProfileUseCase(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public UpdateProfileOutput execute(String token, UpdateProfileInput input) {
    Account account = this.accountRepository.findByAccountId(token).orElseThrow(() -> new IllegalArgumentException("Account not found."));
    if(input.firstName() != null && input.lastName() != null) {
      account.setName(input.firstName(), input.lastName());
    }
    if(input.cpf() != null) {
      account.setCpf(input.cpf());
    }
    if(input.birthDate() != null) {
      account.setBirthDate(input.birthDate());
    }
    if(input.phone() != null) {
      account.setPhone(input.phone());
    }
    if(input.email() != null) {
      account.setEmail(input.email());
    }
    this.accountRepository.save(account);
    return new UpdateProfileOutput(account.getAccountId(), account.getFirstName(), account.getLastName(), account.getCpf(), account.getBirthDate(), account.getPhone(), account.getEmail(), account.getAccountAvatar());
  }
}
