package com.easecell.ease_cell.account.application.gateway;

import com.easecell.ease_cell.account.application.dto.SendEmailInput;

public interface EmailGateway {
  void sendEmail(SendEmailInput input);
}
