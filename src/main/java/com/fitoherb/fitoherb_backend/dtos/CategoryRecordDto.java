package com.fitoherb.fitoherb_backend.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRecordDto(@NotBlank String name) {
}
