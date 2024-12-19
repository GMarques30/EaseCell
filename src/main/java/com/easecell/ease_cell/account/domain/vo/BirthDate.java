package com.easecell.ease_cell.account.domain.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BirthDate {
  private final LocalDate birthDate;

  public BirthDate(String birthDate) {
    if(!birthDate.matches("^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) throw new IllegalArgumentException("Invalid date");
    DateTimeFormatter PATTERN_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    this.birthDate = LocalDate.parse(birthDate, PATTERN_DATE);
  }

  public String getBirthDate() {
    return this.birthDate.toString();
  }
}
