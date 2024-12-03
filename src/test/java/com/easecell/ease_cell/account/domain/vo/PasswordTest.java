package com.easecell.ease_cell.account.domain.vo;

import com.easecell.ease_cell.domain.vo.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class PasswordTest {
  @Test
  @DisplayName("Should be able to create a valid password")
  public void should_be_able_to_create_a_valid_password() {
    assertDoesNotThrow(() -> Password.create("Abc123!"));
    assertDoesNotThrow(() -> Password.create("P@ssw0rd"));
    assertDoesNotThrow(() -> Password.create("A1b$C2d"));
    assertDoesNotThrow(() -> Password.create("P@55word"));
    assertDoesNotThrow(() -> Password.create("Zx1@rT"));
  }

  @Test
  @DisplayName("Should be able to restore a password")
  public void should_be_able_to_restore_a_password() {
    assertDoesNotThrow(() -> Password.restore("8d35b4ce128ec8c5ca7e8e25104ad94129705333dd97d447a04eb3d00cd6fdc5"));
  }

  @Test
  @DisplayName("Should be able to return password")
  public void should_be_able_to_return_password() {
    var password = Password.create("Abc123!");
    assertThat(password).isInstanceOf(Password.class);
  }

  @Test
  @DisplayName("Should be able to return true if the passwords are the same")
  public void should_be_able_to_return_true_if_the_passwords_are_the_same() {
    var password = Password.create("Abc123!");
    assertThat(password.passwordMatches("Abc123!")).isTrue();
  }

  @Test
  @DisplayName("Should not be able to create a invalid password")
  public void should_not_be_able_to_create_a_invalid_password() {
    assertThrows(IllegalArgumentException.class, () -> Password.create("abc123"));
    assertThrows(IllegalArgumentException.class, () -> Password.create("ABC123!"));
    assertThrows(IllegalArgumentException.class, () -> Password.create("Abc!"));
    assertThrows(IllegalArgumentException.class, () -> Password.create("Password_1"));
    assertThrows(IllegalArgumentException.class, () -> Password.create("Abcdefgh"));
  }

  @Test
  @DisplayName("Should be able to catch a NoSuchAlgorithmException")
  public void should_be_able_to_catch_a_no_such_algorithm_exception() {
    try (var mock = mockStatic(MessageDigest.class)){
      mock.when(() -> MessageDigest.getInstance("SHA-256"))
              .thenThrow(new NoSuchAlgorithmException());
      RuntimeException exception = assertThrows(RuntimeException.class, () -> Password.create("Valid@123"));
      assertThat(exception.getMessage()).isEqualTo("SHA-256 algorithm not found");
    }
  }
}
