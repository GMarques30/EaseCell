package com.easecell.ease_cell.account.domain.entity;

import com.easecell.ease_cell.account.domain.vo.*;

import java.util.UUID;

public class Account {
  private final UUID accountId;
  private Name name;
  private CPF cpf;
  private BirthDate birthDate;
  private Phone phone;
  private Email email;
  private Password password;
  private String accountAvatar;

  public Account(String firstName, String lastName, String cpf, String birthDate, String phone, String email, String password) {
    this.accountId = UUID.randomUUID();
    this.name = new Name(firstName, lastName);
    this.cpf = new CPF(cpf);
    this.birthDate = new BirthDate(birthDate);
    this.phone = new Phone(phone);
    this.email = new Email(email);
    this.password = Password.create(password);
    this.accountAvatar = generateDefaultAvatar();
  }

  public String getAccountId() { return this.accountId.toString(); }

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
    if(this.accountAvatar.contains("ui-avatars.com")) {
      this.accountAvatar = generateDefaultAvatar();
    }
  }

  public String getCpf() {
    return this.cpf.getCpf();
  }

  public void setCpf(String cpf) {
    this.cpf = new CPF(cpf);
  }

  public String getBirthDate() {
    return this.birthDate.getBirthDate();
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = new BirthDate(birthDate);
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

  public String generateDefaultAvatar() { return String.format("https://ui-avatars.com/api/?name=%s+%s&background=random", this.getFirstName(), this.getLastName()); }

  public String getAccountAvatar() {
    return this.accountAvatar;
  }

  public void updateAvatar(String avatarUrl) {
    if(avatarUrl == null || avatarUrl.isBlank()) {
      this.accountAvatar = generateDefaultAvatar();
    } else {
      this.accountAvatar = avatarUrl;
    }
  }
}
