package com.fitoherb.fitoherb_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record ProductEditDto(@NotBlank String productName, @NotNull int price_in_cents, @NotBlank String productDescription, @NotBlank
String productCategory, Optional<MultipartFile> productImageUrl, @NotBlank String supplier) {
}
