package com.easecell.ease_cell.account.application.dto;

public record CreateAccountInput(String firstName, String lastName, String cpf, String birthDate, String phone, String email, String password) {
}
