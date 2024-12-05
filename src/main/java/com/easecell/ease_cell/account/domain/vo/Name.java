package com.easecell.ease_cell.account.domain.vo;

public class Name {
  private final String firstName;
  private final String lastName;

  public Name(String firstName, String lastName) {
    if(!firstName.matches("[a-zA-z]+") || !lastName.matches("[a-zA-z]+")) throw new IllegalArgumentException("Invalid name");
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getName() {
    return String.format("%s %s", this.firstName, this.lastName);
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }
}
