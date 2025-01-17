package com.fitoherb.fitoherb_backend.dtos;

import com.fitoherb.fitoherb_backend.models.CategoryModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductRecordDto(@NotBlank String productName, @NotNull int price_in_cents, @NotBlank String productDescription, @NotBlank
String productCategory, @NotBlank String productImageUrl, @NotBlank String supplier) {
}
