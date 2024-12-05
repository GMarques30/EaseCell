package com.easecell.ease_cell.account.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountTest {
  private Account account;

  @BeforeEach
  public void beforeEach() {
    account = new Account("John", "Doe", "123.456.789-09", "1998-07-03", "(11) 91234-5678", "john.doe@example.com", "John@123");
  }

  @Test
  @DisplayName("Should be able to create a account entity")
  public void should_be_able_to_create_an_account_entity() {
    assertNotNull(account.accountId);
    assertThat(account.getName()).isEqualTo("John Doe");
    assertThat(account.getFirstName()).isEqualTo("John");
    assertThat(account.getLastName()).isEqualTo("Doe");
    assertThat(account.getCpf()).isEqualTo("12345678909");
    assertThat(account.getBirthDate()).isEqualTo("03-07-1998");
    assertThat(account.getPhone()).isEqualTo("11912345678");
    assertThat(account.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(account.getAccountAvatar()).isEqualTo("https://ui-avatars.com/api/?name=John+Doe&background=random");
  }

  @Test
  @DisplayName("Should be able to change the account name")
  public void should_be_able_to_change_the_account_name() {
    account.setName("Luis", "Fernando");
    assertThat(account.getFirstName()).isEqualTo("Luis");
    assertThat(account.getLastName()).isEqualTo("Fernando");
    assertThat(account.getName()).isEqualTo("Luis Fernando");
  }

  @Test
  @DisplayName("Should be able to change the cpf")
  public void should_be_able_to_change_the_cpf() {
    account.setCpf("529.982.247-25");
    assertThat(account.getCpf()).isEqualTo("52998224725");
  }

  @Test
  @DisplayName("Should be able to change the birth date")
  public void should_be_able_to_change_the_birth_date() {
    account.setBirthDate("1999-05-20");
    assertThat(account.getBirthDate()).isEqualTo("20-05-1999");
  }

  @Test
  @DisplayName("Should be able to change a phone")
  public void should_be_able_to_change_a_phone() {
    account.setPhone("(51) 99321-0987");
    assertThat(account.getPhone()).isEqualTo("51993210987");
  }

  @Test
  @DisplayName("Should be able to change email")
  public void should_be_able_to_change_email() {
    account.setEmail("other.email@example.com");
    assertThat(account.getEmail()).isEqualTo("other.email@example.com");
  }

  @Test
  @DisplayName("Should be able to change the password")
  public void should_be_able_to_change_the_password() {
    account.setPassword("Doe@1234");
    assertThat(account.passwordMatches("Doe@1234")).isTrue();
  }

  @Test
  @DisplayName("Should be able to add an avatar image an account")
  public void should_be_able_to_add_an_avatar_image_an_account() {
    String avatarUrl = String.format("https://easecell-avatars.s3.amazonaws.com/avatars/%s/avatar-image.jpg", account.accountId);
    account.setAccountAvatar(avatarUrl);
    assertThat(account.getAccountAvatar()).isEqualTo(avatarUrl);
  }

  @Test
  @DisplayName("Should be possible to compare whether two passwords are the same")
  public void should_be_possible_to_compare_whether_two_passwords_are_the_same() {
    assertThat(account.passwordMatches("John@123")).isTrue();
  }
}
