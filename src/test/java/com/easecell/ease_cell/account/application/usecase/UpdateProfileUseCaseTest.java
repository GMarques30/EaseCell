package com.easecell.ease_cell.account.application.usecase;

import com.easecell.ease_cell.account.application.dto.UpdateProfileInput;
import com.easecell.ease_cell.account.application.repository.AccountRepository;
import com.easecell.ease_cell.account.domain.entity.Account;
import com.easecell.ease_cell.account.infra.repository.AccountRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateProfileUseCaseTest {
  private AccountRepository accountRepository;
  private UpdateProfileUseCase sut;
  private Account account;

  @BeforeEach
  public void beforeEach() {
    this.accountRepository = new AccountRepositoryMemory();
    this.sut = new UpdateProfileUseCase(accountRepository);
    account = new Account("John", "Doe", "123.456.789-09", "1999-06-09", "11981674321", "john.doe@example.com", "John@1234");
    this.accountRepository.save(account);
  }

  @Test
  @DisplayName("Should be able to profile data update")
  public void should_be_able_to_profile_data_update() {
    UpdateProfileInput input = new UpdateProfileInput("Fernando", "Luiz", "148.741.545-14", "1998-07-02", "16997665544", "fernando.luiz@example.com");
    var accountUpdated = this.sut.execute(account.getAccountId(), input);
    assertThat(accountUpdated.firstName()).isEqualTo(input.firstName());
    assertThat(accountUpdated.lastName()).isEqualTo(input.lastName());
    assertThat(accountUpdated.cpf()).isEqualTo(input.cpf().replaceAll("\\D", ""));
    assertThat(accountUpdated.birthDate()).isEqualTo(input.birthDate());
    assertThat(accountUpdated.phone()).isEqualTo(input.phone());
    assertThat(accountUpdated.email()).isEqualTo(input.email());
  }

  @Test
  @DisplayName("Should only update the name")
  public void should_only_update_the_name() {
    UpdateProfileInput input = new UpdateProfileInput("Fernando", "Luiz", null, null, null, null);
    var accountUpdated = this.sut.execute(account.getAccountId(), input);
    assertThat(accountUpdated.firstName()).isEqualTo(input.firstName());
    assertThat(accountUpdated.lastName()).isEqualTo(input.lastName());
    assertThat(accountUpdated.cpf()).isEqualTo(account.getCpf());
    assertThat(accountUpdated.birthDate()).isEqualTo(account.getBirthDate());
    assertThat(accountUpdated.phone()).isEqualTo(account.getPhone());
    assertThat(accountUpdated.email()).isEqualTo(account.getEmail());
  }

  @Test
  @DisplayName("Should only update the cpf")
  public void should_only_update_the_cpf() {
    UpdateProfileInput input = new UpdateProfileInput(null, null, "148.741.545-14", null, null, null);
    var accountUpdated = this.sut.execute(account.getAccountId(), input);
    assertThat(accountUpdated.firstName()).isEqualTo(account.getFirstName());
    assertThat(accountUpdated.lastName()).isEqualTo(account.getLastName());
    assertThat(accountUpdated.cpf()).isEqualTo(input.cpf().replaceAll("\\D", ""));
    assertThat(accountUpdated.birthDate()).isEqualTo(account.getBirthDate());
    assertThat(accountUpdated.phone()).isEqualTo(account.getPhone());
    assertThat(accountUpdated.email()).isEqualTo(account.getEmail());
  }

  @Test
  @DisplayName("Should only update the birth date")
  public void should_only_update_the_birth_date() {
    UpdateProfileInput input = new UpdateProfileInput(null, null, null, "1998-07-02", null, null);
    var accountUpdated = this.sut.execute(account.getAccountId(), input);
    assertThat(accountUpdated.firstName()).isEqualTo(account.getFirstName());
    assertThat(accountUpdated.lastName()).isEqualTo(account.getLastName());
    assertThat(accountUpdated.cpf()).isEqualTo(account.getCpf());
    assertThat(accountUpdated.birthDate()).isEqualTo(input.birthDate());
    assertThat(accountUpdated.phone()).isEqualTo(account.getPhone());
    assertThat(accountUpdated.email()).isEqualTo(account.getEmail());
  }

  @Test
  @DisplayName("Should only update the phone")
  public void should_only_update_the_phone() {
    UpdateProfileInput input = new UpdateProfileInput(null, null, null, null, "11981674321", null);
    var accountUpdated = this.sut.execute(account.getAccountId(), input);
    assertThat(accountUpdated.firstName()).isEqualTo(account.getFirstName());
    assertThat(accountUpdated.lastName()).isEqualTo(account.getLastName());
    assertThat(accountUpdated.cpf()).isEqualTo(account.getCpf());
    assertThat(accountUpdated.birthDate()).isEqualTo(account.getBirthDate());
    assertThat(accountUpdated.phone()).isEqualTo(input.phone());
    assertThat(accountUpdated.email()).isEqualTo(account.getEmail());
  }

  @Test
  @DisplayName("Should only update the email")
  public void should_only_update_the_email() {
    UpdateProfileInput input = new UpdateProfileInput(null, null, null, null, null, "john.doe1@example.com");
    var accountUpdated = this.sut.execute(account.getAccountId(), input);
    assertThat(accountUpdated.firstName()).isEqualTo(account.getFirstName());
    assertThat(accountUpdated.lastName()).isEqualTo(account.getLastName());
    assertThat(accountUpdated.cpf()).isEqualTo(account.getCpf());
    assertThat(accountUpdated.birthDate()).isEqualTo(account.getBirthDate());
    assertThat(accountUpdated.phone()).isEqualTo(account.getPhone());
    assertThat(accountUpdated.email()).isEqualTo(input.email());
  }

  @Test
  @DisplayName("Should throw exception when account id does not existing")
  public void should_throw_exception_when_account_id_does_not_existing() {
    UpdateProfileInput input = new UpdateProfileInput("Fernando", "Luiz", "148.741.545-14", "1998-07-02", "16997665544", "fernando.luiz@example.com");
    assertThrows(IllegalArgumentException.class, () -> this.sut.execute(UUID.randomUUID().toString(), input));
  }
}
