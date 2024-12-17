package com.easecell.ease_cell.account.infra.controller;

import com.easecell.ease_cell.account.application.dto.AuthenticationInput;
import com.easecell.ease_cell.account.application.dto.AuthenticationOutput;
import com.easecell.ease_cell.account.application.dto.CreateAccountInput;
import com.easecell.ease_cell.account.application.dto.SendForgotPasswordInput;
import com.easecell.ease_cell.account.application.usecase.AuthenticationUseCase;
import com.easecell.ease_cell.account.application.usecase.CreateAccountUseCase;
import com.easecell.ease_cell.account.application.usecase.SendForgotPasswordUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
  private final CreateAccountUseCase createAccountUseCase;
  private final AuthenticationUseCase authenticationUseCase;
  private final SendForgotPasswordUseCase sendForgotPasswordUseCase;

  public AccountController(CreateAccountUseCase createAccountUseCase, AuthenticationUseCase authenticationUseCase, SendForgotPasswordUseCase sendForgotPasswordUseCase) {
    this.createAccountUseCase = createAccountUseCase;
    this.authenticationUseCase = authenticationUseCase;
    this.sendForgotPasswordUseCase = sendForgotPasswordUseCase;
  }

  @PostMapping("/register")
  public ResponseEntity<Void> createAccount(@RequestBody CreateAccountInput input) {
    try {
      this.createAccountUseCase.execute(input);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationOutput> authentication(@RequestBody AuthenticationInput input) {
    try {
      var output = this.authenticationUseCase.execute(input);
      return ResponseEntity.status(HttpStatus.OK).body(output);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  @PostMapping("/forgot")
  public ResponseEntity<Void> sendForgotPassword(@RequestBody SendForgotPasswordInput input) {
    try {
      this.sendForgotPasswordUseCase.execute(input);
      return ResponseEntity.status(HttpStatus.OK).build();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
