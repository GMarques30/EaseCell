package com.easecell.ease_cell.account.infra.repository;

import com.easecell.ease_cell.account.application.repository.ResetTokenRepository;
import com.easecell.ease_cell.account.domain.entity.ResetToken;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ResetTokenRepositoryMemory implements ResetTokenRepository {
  private final List<ResetToken> resetTokens;

  public ResetTokenRepositoryMemory() {
    this.resetTokens = new ArrayList<>();
  }

  @Override
  public void save(ResetToken resetToken) {
    this.resetTokens.add(resetToken);
  }

  @Override
  public Optional<ResetToken> findByResetTokenId(String resetTokenId) {
    return this.resetTokens.stream().filter(token -> token.getResetTokenId().equals(resetTokenId)).findFirst();
  }
}
