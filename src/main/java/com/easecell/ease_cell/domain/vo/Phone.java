package com.easecell.ease_cell.domain.vo;

public class Phone {
  private final String phone;

  public Phone(String phone) {
    if(!phone.matches("^\\(?\\d{2}\\)?[\\s.-]?9\\d{4}[\\s.-]?\\d{4}$")) throw new IllegalArgumentException("Invalid phone");
    this.phone = phone;
  }

  public String getPhone() {
    return this.phone;
  }
}
