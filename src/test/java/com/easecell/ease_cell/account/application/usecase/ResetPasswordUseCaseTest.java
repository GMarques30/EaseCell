package com.easecell.ease_cell.account.application.usecase;

import com.easecell.ease_cell.account.application.dto.ResetTokenInput;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.application.repository.ResetTokenRepository;
import com.easecell.ease_cell.account.domain.entity.Account;
import com.easecell.ease_cell.account.domain.entity.ResetToken;
import com.easecell.ease_cell.account.infra.repository.AccountRepositoryMemory;
import com.easecell.ease_cell.account.infra.repository.ResetTokenRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResetPasswordUseCaseTest {
  private AccountRepository accountRepository;
  private ResetTokenRepository resetTokenRepository;
  private ResetPasswordUseCase sut;
  private Account account;


  @BeforeEach
  public void beforeEach() {
    this.accountRepository = new AccountRepositoryMemory();
    this.resetTokenRepository = new ResetTokenRepositoryMemory();
    this.sut = new ResetPasswordUseCase(resetTokenRepository, accountRepository);
    account = new Account("John", "Doe", "123.456.789-09", "1998-10-09", "11997654321", "john.doe@example.com", "Jwm@1234");
    this.accountRepository.save(account);
  }

  @Test
  @DisplayName("Should allow resetting the password when the token is valid")
  public void should_allow_resetting_the_password_when_the_token_is_valid() {
    ResetToken token = new ResetToken(account.getAccountId());
    this.resetTokenRepository.save(token);
    String newPassword = "Abc@1234";
    this.sut.execute(new ResetTokenInput(token.getResetTokenId().toString(), newPassword));
    Account updatedAccount = this.accountRepository.findByAccountId(account.getAccountId().toString()).orElseThrow();
    assertThat(updatedAccount.passwordMatches(newPassword)).isTrue();
  }

  @Test
  @DisplayName("Should throw an exception if the token is not found")
  public void should_throw_an_exception_if_the_token_is_not_found() {
    String newPassword = "Abc@1234";
    assertThrows(IllegalArgumentException.class, () -> this.sut.execute(new ResetTokenInput("token-non-existent", newPassword)));
  }

  @Test
  @DisplayName("Should not be possible to reset the password when the token is invalid")
  public void should_not_be_possible_to_reset_the_password_when_the_token_is_invalid() {
    LocalDateTime dateThreeHoursInThePast = LocalDateTime.now().minusHours(3L);
    ResetToken tokenExpired = new ResetToken(account.getAccountId(), dateThreeHoursInThePast);
    this.resetTokenRepository.save(tokenExpired);
    String newPassword = "Abc@1234";
    assertThrows(IllegalArgumentException.class, () -> this.sut.execute(new ResetTokenInput(tokenExpired.getResetTokenId().toString(), newPassword)));
  }

  @Test
  @DisplayName("Should throw an exception if the account is not found")
  public void should_throw_an_exception_if_the_account_is_not_found() {
    ResetToken token = new ResetToken(UUID.randomUUID());
    this.resetTokenRepository.save(token);
    String newPassword = "Abc@1234";
    assertThrows(IllegalArgumentException.class, () -> this.sut.execute(new ResetTokenInput(token.getResetTokenId().toString(), newPassword)));
  }
}
