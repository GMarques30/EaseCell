package com.easecell.ease_cell.domain.entity;

import com.easecell.ease_cell.domain.vo.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Account {
  public final String accountId;
  private Name name;
  private CPF cpf;
  private String birthDate;
  private Phone phone;
  private Email email;
  private Password password;
  private String accountAvatar;

  public Account(String firstName, String lastName, String cpf, String birthDate, String phone, String email, String password) {
    this.accountId = UUID.randomUUID().toString();
    this.name = new Name(firstName, lastName);
    this.cpf = new CPF(cpf);
    this.birthDate = formatter(birthDate);
    this.phone = new Phone(phone);
    this.email = new Email(email);
    this.password = Password.create(password);
    this.accountAvatar = String.format("https://ui-avatars.com/api/?name=%s+%s&background=random", this.getFirstName(), this.getLastName());

  }

  public String getName() {
    return this.name.getName();
  }

  public String getFirstName() {
    return this.name.getFirstName();
  }

  public String getLastName() {
    return this.name.getLastName();
  }

  public void setName(String firstName, String lastName) {
    this.name = new Name(firstName, lastName);
  }

  public String getCpf() {
    return this.cpf.getCpf();
  }

  public void setCpf(String cpf) {
    this.cpf = new CPF(cpf);
  }

  public String formatter(String date) {
    DateTimeFormatter formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate dateFormatted = LocalDate.parse(date, formatterInput);
    DateTimeFormatter formatterDateBrl = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return dateFormatted.format(formatterDateBrl);
  }

  public String getBirthDate() {
    return this.birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = formatter(birthDate);
  }

  public String getPhone() {
    return this.phone.getPhone();
  }

  public void setPhone(String phone) {
    this.phone = new Phone(phone);
  }

  public String getEmail() {
    return this.email.getEmail();
  }

  public void setEmail(String email) {
    this.email = new Email(email);
  }

  public boolean passwordMatches(String password) {
    return this.password.passwordMatches(password);
  }

  public void setPassword(String password) {
    this.password = Password.create(password);
  }

  public String getAccountAvatar() {
    return this.accountAvatar;
  }

  public void setAccountAvatar(String accountAvatar) {
    this.accountAvatar = accountAvatar;
  }
}
