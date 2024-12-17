package com.easecell.ease_cell.account.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResetToken {
  private final UUID accountTokenId;
  private final UUID accountId;
  private final LocalDateTime createdAt;

  public ResetToken(UUID accountId) {
    this.accountTokenId = UUID.randomUUID();
    this.accountId = accountId;
    this.createdAt = LocalDateTime.now();
  }

  public ResetToken(UUID accountId, LocalDateTime createdAt) {
    this.accountTokenId = UUID.randomUUID();
    this.accountId = accountId;
    this.createdAt = createdAt;
  }

  public boolean isValidResetToken() {
    return !LocalDateTime.now().isAfter(this.createdAt.plusHours(2L));
  }

  public UUID getAccountTokenId() {
    return this.accountTokenId;
  }

  public UUID getAccountId() {
    return this.accountId;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }
}