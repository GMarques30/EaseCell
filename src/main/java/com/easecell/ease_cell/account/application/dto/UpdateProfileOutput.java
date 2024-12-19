package com.easecell.ease_cell.account.application.dto;

public record UpdateProfileOutput(String accountId, String firstName, String lastName, String cpf, String birthDate, String phone, String email, String avatar) {
}
