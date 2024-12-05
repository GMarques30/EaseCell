package com.easecell.ease_cell.account.domain.vo;

public class CPF {
  private final String cpf;
  private static final int FACTOR_FIRST_DIGIT = 10;
  private static final int FACTOR_SECOND_DIGIT = 11;

  public CPF(String cpf) {
    if (!validate(cpf)) {
      throw new IllegalArgumentException("Invalid CPF");
    }
    this.cpf = cpf.replaceAll("\\D", "");
  }

  public String getCpf() {
    return this.cpf;
  }

  private boolean validate(String rawCpf) {
    String cpf = removeNonDigits(rawCpf);
    if (allDigitsTheSame(cpf)) {
      return false;
    }
    int digit1 = calculateDigit(cpf, FACTOR_FIRST_DIGIT);
    int digit2 = calculateDigit(cpf, FACTOR_SECOND_DIGIT);
    return extractActualDigit(cpf).equals("" + digit1 + digit2);
  }

  private String removeNonDigits(String cpf) {
    return cpf.replaceAll("\\D", "");
  }

  private boolean allDigitsTheSame(String cpf) {
    char firstDigit = cpf.charAt(0);
    for (char digit : cpf.toCharArray()) {
      if (digit != firstDigit) {
        return false;
      }
    }
    return true;
  }

  private int calculateDigit(String cpf, int factor) {
    int total = 0;
    for (int i = 0; i < cpf.length() && factor > 1; i++) {
      int digit = Character.getNumericValue(cpf.charAt(i));
      total += digit * factor--;
    }
    int remainder = total % 11;
    return (remainder < 2) ? 0 : 11 - remainder;
  }

  private String extractActualDigit(String cpf) {
    return cpf.substring(9);
  }
}
