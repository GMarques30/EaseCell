package com.easecell.ease_cell.account.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResetToken {
  private final UUID resetTokenId;
  private final UUID accountId;
  private final LocalDateTime createdAt;

  public ResetToken(String accountId) {
    this.resetTokenId = UUID.randomUUID();
    this.accountId = UUID.fromString(accountId);
    this.createdAt = LocalDateTime.now();
  }

  public ResetToken(String accountId, LocalDateTime createdAt) {
    this.resetTokenId = UUID.randomUUID();
    this.accountId = UUID.fromString(accountId);
    this.createdAt = createdAt;
  }

  public boolean isValidResetToken() {
    return !LocalDateTime.now().isAfter(this.createdAt.plusHours(2L));
  }

  public String getResetTokenId() {
    return this.resetTokenId.toString();
  }

  public String getAccountId() {
    return this.accountId.toString();
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }
}
