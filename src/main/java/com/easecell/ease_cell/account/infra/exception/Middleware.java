package com.easecell.ease_cell.account.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Middleware {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }
}
