package com.easecell.ease_cell.account.application.repository;

import com.easecell.ease_cell.account.domain.entity.ResetToken;

public interface AccountTokenRepository {
  void save(ResetToken resetToken);
}
