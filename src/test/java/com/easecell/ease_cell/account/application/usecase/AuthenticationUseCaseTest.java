package com.easecell.ease_cell.account.application.usecase;

import com.easecell.ease_cell.account.application.dto.AuthenticationInput;
import com.easecell.ease_cell.account.application.dto.AuthenticationOutput;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.domain.entity.Account;
import com.easecell.ease_cell.account.infra.repository.AccountRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationUseCaseTest {
  private AccountRepository accountRepository;
  @InjectMocks
  private AuthenticationUseCase sut;

  @BeforeEach
  public void beforeEach() {
    try {
      this.accountRepository = new AccountRepositoryMemory();
      this.sut = new AuthenticationUseCase(accountRepository);
      MockitoAnnotations.openMocks(this);
      var secretKeyField = AuthenticationUseCase.class.getDeclaredField("secretKey");
      secretKeyField.setAccessible(true);
      secretKeyField.set(sut, "test-secret-key");
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("Could not set field: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Should be able to return a token when logging in with a valid email and password")
  public void should_be_able_to_return_a_token_when_logging_in_with_a_valid_email_and_password() {
    Account account = new Account("John", "Doe", "148.741.545-14", "1998-10-27", "(21) 99710-8899", "john.doe@example.com", "John@123");
    this.accountRepository.save(account);
    AuthenticationInput input = new AuthenticationInput(account.getEmail(), "John@123");
    AuthenticationOutput output = this.sut.execute(input);
    assertThat(output.token()).isNotEmpty();
  }

  @Test
  @DisplayName("Should not be able to return a token with email unregistered")
  public void should_not_be_able_to_return_a_token_with_email_unregistered() {
    AuthenticationInput input = new AuthenticationInput("invalid@example.com", "John@123");
    assertThrows(IllegalArgumentException.class, () -> this.sut.execute(input));
  }

  @Test
  @DisplayName("Should not be able to return a token with password invalid")
  public void should_not_be_able_to_return_a_token_with_password_invalid() {
    Account account = new Account("John", "Doe", "148.741.545-14", "1998-10-27", "(21) 99710-8899", "john.doe@example.com", "John@123");
    this.accountRepository.save(account);
    AuthenticationInput input = new AuthenticationInput(account.getEmail(), "Invalid@123");
    assertThrows(IllegalArgumentException.class, () -> this.sut.execute(input));
  }
}
