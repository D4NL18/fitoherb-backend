package com.fitoherb.fitoherb_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRecordDto(@NotBlank String ProductName, @NotNull int price_in_cents) {
}
