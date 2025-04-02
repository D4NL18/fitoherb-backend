package com.fitoherb.fitoherb_backend.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserEditDto(@NotBlank String user_name, @NotBlank String email, String password) {
}
