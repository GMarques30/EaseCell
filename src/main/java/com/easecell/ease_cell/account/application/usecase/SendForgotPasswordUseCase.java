package com.easecell.ease_cell.account.application.usecase;

import com.easecell.ease_cell.account.application.dto.SendEmailInput;
import com.easecell.ease_cell.account.application.dto.SendForgotPasswordInput;
import com.easecell.ease_cell.account.application.gateway.EmailGateway;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.application.repository.AccountTokenRepository;
import com.easecell.ease_cell.account.domain.entity.ResetToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendForgotPasswordUseCase {
  private final AccountRepository accountRepository;
  private final AccountTokenRepository accountTokenRepository;
  private final EmailGateway emailGateway;

  @Value("${api.app.web.url}")
  private String appWebUrl;

  public SendForgotPasswordUseCase(AccountRepository accountRepository, AccountTokenRepository accountTokenRepository, EmailGateway emailGateway) {
    this.accountRepository = accountRepository;
    this.accountTokenRepository = accountTokenRepository;
    this.emailGateway = emailGateway;
  }

  public void execute(SendForgotPasswordInput input) {
    var account = this.accountRepository.findByEmail(input.email()).orElseThrow(() -> new IllegalArgumentException("Account not found."));
    var resetToken = new ResetToken(account.getAccountId());
    String resetPasswordLink = String.format("%s/reset-password?token=%s", appWebUrl, resetToken.getAccountTokenId());
    var sendEmailInput = new SendEmailInput(account.getName(), account.getEmail(), resetPasswordLink);
    this.emailGateway.sendEmail(sendEmailInput);
    this.accountTokenRepository.save(resetToken);
  }
}
