package com.easecell.ease_cell.account.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ResetTokenTest {

  private ResetToken resetToken;

  @BeforeEach
  public void beforeEach() {
    this.resetToken = new ResetToken(UUID.randomUUID());
  }

  @Test
  @DisplayName("Should be able to create a valid reset token")
  public void should_be_able_to_create_a_valid_reset_token() {
    assertThat(resetToken.getAccountTokenId()).isInstanceOf(UUID.class);
    assertThat(resetToken.getAccountTokenId()).isNotNull();
    assertThat(resetToken.getAccountId()).isInstanceOf(UUID.class);
    assertThat(resetToken.getAccountId()).isNotNull();
    assertThat(resetToken.getCreatedAt()).isInstanceOf(LocalDateTime.class);
    assertThat(resetToken.getCreatedAt()).isNotNull();
  }

  @Test
  @DisplayName("Should be able to check if reset token is valid")
  public void should_be_able_to_check_if_reset_token_is_valid() {
    assertThat(resetToken.isValidResetToken()).isTrue();
  }

  @Test
  @DisplayName("Should return false if the reset tokens expiration time has passed")
    public void should_return_false_if_the_reset_tokens_expiration_time_has_passed() {
      LocalDateTime dateTimeExpired = LocalDateTime.now().minusHours(3L);
      ResetToken resetTokenExpired = new ResetToken(UUID.randomUUID(), dateTimeExpired);
      assertThat(resetTokenExpired.isValidResetToken()).isFalse();
  }
}
