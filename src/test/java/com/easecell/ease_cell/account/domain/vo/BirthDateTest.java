package com.easecell.ease_cell.account.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BirthDateTest {
  @Test
  @DisplayName("Should be able to create a birth date valid")
  public void should_be_able_to_create_a_birth_date_valid() {
    assertDoesNotThrow(() -> new BirthDate("1998-12-01"));
    assertDoesNotThrow(() -> new BirthDate("2001-08-23"));
    assertDoesNotThrow(() -> new BirthDate("1948-01-28"));
  }

  @Test
  @DisplayName("Should not be able to create an invalid birth date")
  public void should_not_be_able_to_create_an_invalid_birth_date() {
    assertThrows(IllegalArgumentException.class, () -> new BirthDate(""));
    assertThrows(IllegalArgumentException.class, () -> new BirthDate("any string"));
    assertThrows(IllegalArgumentException.class, () -> new BirthDate("-/@"));
    assertThrows(IllegalArgumentException.class, () -> new BirthDate("89-10-01"));
    assertThrows(IllegalArgumentException.class, () -> new BirthDate("89-10-01"));
    assertThrows(IllegalArgumentException.class, () -> new BirthDate("1989-13-01"));
    assertThrows(IllegalArgumentException.class, () -> new BirthDate("1989-10-32"));
  }
}
