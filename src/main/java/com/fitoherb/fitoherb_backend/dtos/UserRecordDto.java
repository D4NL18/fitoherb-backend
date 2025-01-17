package com.fitoherb.fitoherb_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRecordDto(@NotBlank String user_name, @NotBlank String email, @NotBlank String password) {
}
