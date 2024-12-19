package com.easecell.ease_cell.account.application.usecase;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.easecell.ease_cell.account.application.dto.AuthenticationInput;
import com.easecell.ease_cell.account.application.dto.AuthenticationOutput;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthenticationUseCase {
  private final AccountRepository accountRepository;

  @Value("${api.security.token.secret}")
  private String secretKey;

  public AuthenticationUseCase(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public AuthenticationOutput execute(AuthenticationInput input) {
    var account = this.accountRepository.findByEmail(input.email()).orElseThrow(() -> new IllegalArgumentException("Account not found."));
    if(!account.passwordMatches(input.password())) throw new IllegalArgumentException("Invalid credentials.");
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    String token = JWT.create()
            .withIssuer("easecell-authenticate")
            .withSubject(account.getAccountId())
            .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
            .sign(algorithm);
    return new AuthenticationOutput(token);
  }
}
