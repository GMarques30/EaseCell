package com.easecell.ease_cell.account.application.usecase;

import com.easecell.ease_cell.account.application.dto.SendEmailInput;
import com.easecell.ease_cell.account.application.dto.SendForgotPasswordInput;
import com.easecell.ease_cell.account.application.gateway.EmailGateway;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.application.repository.ResetTokenRepository;
import com.easecell.ease_cell.account.domain.entity.Account;
import com.easecell.ease_cell.account.infra.repository.AccountRepositoryMemory;
import com.easecell.ease_cell.account.infra.repository.ResetTokenRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SendForgotPasswordUseCaseTest {

  private ResetTokenRepository resetTokenRepository;
  private AccountRepository accountRepository;
  @Mock
  private EmailGateway emailGateway;
  @InjectMocks
  private SendForgotPasswordUseCase sut;

  @BeforeEach
  public void beforeEach() {
    MockitoAnnotations.openMocks(this);
    this.accountRepository = new AccountRepositoryMemory();
    this.resetTokenRepository = new ResetTokenRepositoryMemory();
    this.sut = new SendForgotPasswordUseCase(accountRepository, resetTokenRepository, emailGateway);
  }

  @Test
  @DisplayName("Should send forgot password when account exists")
  public void should_send_forgot_password_when_account_exists() {
    Account account = new Account("John", "Doe", "123.456.789-09", "1998-10-09", "11997654321", "john.doe@example.com", "Jwm@1234");
    this.accountRepository.save(account);
    ArgumentCaptor<SendEmailInput> emailInputCaptor = ArgumentCaptor.forClass(SendEmailInput.class);
    this.sut.execute(new SendForgotPasswordInput(account.getEmail()));
    verify(emailGateway, times(1)).sendEmail(emailInputCaptor.capture());
    SendEmailInput capturedInput = emailInputCaptor.getValue();
    assertThat(account.getName()).isEqualTo(capturedInput.name());
    assertThat(account.getEmail()).isEqualTo(capturedInput.to());
    String resetToken = capturedInput.link().split("=")[1];
    assertThat(resetToken).isNotEmpty();
  }

  @Test
  @DisplayName("Should throw exception if account does not exists")
  public void should_throw_exception_if_account_does_not_exists() {
    String email = "nonexistent@example.com";
    assertThrows(IllegalArgumentException.class, () -> this.sut.execute(new SendForgotPasswordInput(email)));
    verifyNoMoreInteractions(emailGateway);
  }
}
