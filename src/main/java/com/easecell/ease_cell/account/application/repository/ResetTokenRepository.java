package com.easecell.ease_cell.account.application.repository;

import com.easecell.ease_cell.account.domain.entity.ResetToken;

import java.util.Optional;

public interface ResetTokenRepository {
  void save(ResetToken resetToken);
  Optional<ResetToken> findByResetTokenId(String resetTokenId);
}
