package com.easecell.ease_cell.account.application.usecase;

import com.easecell.ease_cell.account.application.dto.CreateAccountInput;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.infra.repository.AccountRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateAccountUseCaseTest {
  private AccountRepository accountRepository;
  private CreateAccountUseCase sut;

  @BeforeEach
  public void beforeEach() {
    this.accountRepository = new AccountRepositoryMemory();
    this.sut = new CreateAccountUseCase(accountRepository);
  }

  @Test
  @DisplayName("Should be possible to create an account")
  public void should_be_possible_to_create_an_account() {
    var input = new CreateAccountInput("John", "Doe", "148.741.545-14", "1998-10-27", "(21) 99710-8899", "john.doe@example.com", "John@123");
    this.sut.execute(input);
    var account = this.accountRepository.findByEmail(input.email()).orElseThrow();
    assertThat(account.getAccountId()).isNotNull();
    assertThat(account.getFirstName()).isEqualTo(input.firstName());
    assertThat(account.getLastName()).isEqualTo(input.lastName());
    assertThat(account.getCpf()).isEqualTo("14874154514");
    assertThat(account.getBirthDate()).isEqualTo("1998-10-27");
    assertThat(account.getPhone()).isEqualTo("21997108899");
    assertThat(account.getEmail()).isEqualTo(input.email());
  }

  @Test
  @DisplayName("Should not be possible to create an existing account")
  public void should_not_be_possible_to_create_an_existing_account() {
    var input = new CreateAccountInput("John", "Doe", "148.741.545-14", "1998-10-27", "(21) 99710-8899", "john.doe@example.com", "John@123");
    this.sut.execute(input);
    assertThrows(IllegalArgumentException.class, () -> this.sut.execute(input));
  }
}
