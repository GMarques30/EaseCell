package com.easecell.ease_cell.account.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CpfTest {
  @Test
  @DisplayName("Should be able to create a valid cpf")
  public void should_be_able_to_create_a_valid_cpf() {
    assertDoesNotThrow(() -> new CPF("148.741.545-14"));
    assertDoesNotThrow(() -> new CPF("480.012.893-54"));
    assertDoesNotThrow(() -> new CPF("859.113.292-04"));
    assertDoesNotThrow(() -> new CPF("349.881.256-45"));
    assertDoesNotThrow(() -> new CPF("219.200.435-43"));
  }

  @Test
  @DisplayName("Should be able to create a invalid cpf")
  public void should_not_be_able_to_create_a_invalid_cpf() {
    assertThrows(IllegalArgumentException.class, () -> new CPF("989.046.923-51"));
    assertThrows(IllegalArgumentException.class, () -> new CPF("111.111.111-11"));
    assertThrows(IllegalArgumentException.class, () -> new CPF("529.506.431-02"));
    assertThrows(IllegalArgumentException.class, () -> new CPF("268.363.928-79"));
    assertThrows(IllegalArgumentException.class, () -> new CPF("129.564.321-98"));
  }
}
