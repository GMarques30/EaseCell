package com.easecell.ease_cell.account.application.usecase;

import com.easecell.ease_cell.account.application.dto.ResetPasswordInput;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.application.repository.ResetTokenRepository;
import com.easecell.ease_cell.account.domain.entity.Account;
import com.easecell.ease_cell.account.domain.entity.ResetToken;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordUseCase {
  private final AccountRepository accountRepository;
  private final ResetTokenRepository resetTokenRepository;

  public ResetPasswordUseCase(ResetTokenRepository resetTokenRepository, AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
    this.resetTokenRepository = resetTokenRepository;
  }

  public void execute(String resetToken, ResetPasswordInput input) {
    ResetToken token = this.resetTokenRepository.findByResetTokenId(resetToken).orElseThrow(() -> new IllegalArgumentException("Reset token not found."));
    if(!token.isValidResetToken()) throw new IllegalArgumentException("Reset token expired.");
    Account account = this.accountRepository.findByAccountId(token.getAccountId()).orElseThrow(() -> new IllegalArgumentException("Account not found."));
    account.setPassword(input.password());
    this.accountRepository.save(account);
  }
}
