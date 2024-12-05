package com.easecell.ease_cell.account.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhoneTest {
  @Test
  @DisplayName("Should be able to create a valid phone")
  public void should_be_able_to_create_a_valid_phone() {
    assertDoesNotThrow(() -> new Phone("(11) 98765-4321"));
    assertDoesNotThrow(() -> new Phone("21 98765-4321"));
    assertDoesNotThrow(() -> new Phone("(31) 99876-5432"));
    assertDoesNotThrow(() -> new Phone("41 91234-5678"));
    assertDoesNotThrow(() -> new Phone("(11) 987654321"));
  }

  @Test
  @DisplayName("Should not be able to create a invalid phone")
  public void should_not_be_able_to_create_a_invalid_phone() {
    assertThrows(IllegalArgumentException.class, () -> new Phone("98765-4321"));
    assertThrows(IllegalArgumentException.class, () -> new Phone("(21) 9876-5432"));
    assertThrows(IllegalArgumentException.class, () -> new Phone("11 98765-43210"));
    assertThrows(IllegalArgumentException.class, () -> new Phone("(22) 9-8765-4321"));
  }
}
