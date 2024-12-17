package com.easecell.ease_cell.account.infra.repository;

import com.easecell.ease_cell.account.application.repository.AccountTokenRepository;
import com.easecell.ease_cell.account.domain.entity.ResetToken;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountTokenRepositoryMemory implements AccountTokenRepository{
  private final List<ResetToken> resetTokens;

  public AccountTokenRepositoryMemory() {
    this.resetTokens = new ArrayList<>();
  }

  @Override
  public void save(ResetToken resetToken) {
    this.resetTokens.add(resetToken);
  }
}
