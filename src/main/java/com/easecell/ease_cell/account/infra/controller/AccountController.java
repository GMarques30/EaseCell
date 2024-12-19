package com.easecell.ease_cell.account.infra.controller;

import com.easecell.ease_cell.account.application.dto.*;
import com.easecell.ease_cell.account.application.usecase.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

  private final CreateAccountUseCase createAccountUseCase;
  private final AuthenticationUseCase authenticationUseCase;
  private final SendForgotPasswordUseCase sendForgotPasswordUseCase;
  private final ResetPasswordUseCase resetPasswordUseCase;
  private final UpdateProfileUseCase updateProfileUseCase;

  public AccountController(CreateAccountUseCase createAccountUseCase, AuthenticationUseCase authenticationUseCase,
                           SendForgotPasswordUseCase sendForgotPasswordUseCase, ResetPasswordUseCase resetPasswordUseCase,
                           UpdateProfileUseCase updateProfileUseCase) {
    this.createAccountUseCase = createAccountUseCase;
    this.authenticationUseCase = authenticationUseCase;
    this.sendForgotPasswordUseCase = sendForgotPasswordUseCase;
    this.resetPasswordUseCase = resetPasswordUseCase;
    this.updateProfileUseCase = updateProfileUseCase;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void createAccount(@RequestBody CreateAccountInput input) {
    this.createAccountUseCase.execute(input);
  }

  @PostMapping("/login")
  public @ResponseBody AuthenticationOutput authentication(@RequestBody AuthenticationInput input) {
    return this.authenticationUseCase.execute(input);
  }

  @PostMapping("/forgot")
  @ResponseStatus(HttpStatus.OK)
  public void sendForgotPassword(@RequestBody SendForgotPasswordInput input) {
    this.sendForgotPasswordUseCase.execute(input);
  }

  @PostMapping("/reset-password/{resetToken}")
  @ResponseStatus(HttpStatus.OK)
  public void resetPassword(@PathVariable("resetToken") String resetToken, @RequestBody ResetPasswordInput input) {
    this.resetPasswordUseCase.execute(resetToken, input);
  }

  @PutMapping("/me/{accountId}")
  @ResponseBody
  public UpdateProfileOutput updateProfile(@PathVariable("accountId") String accountId, @RequestBody UpdateProfileInput input) {
    return this.updateProfileUseCase.execute(accountId, input);
  }
}
