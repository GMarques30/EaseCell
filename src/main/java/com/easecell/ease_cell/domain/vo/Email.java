package com.easecell.ease_cell.domain.vo;

public class Email {
  private final String email;

  public Email(String email) {
    if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) throw new IllegalArgumentException("Invalid email");
    this.email = email;
  }

  public String getEmail() {
    return this.email;
  }
}
