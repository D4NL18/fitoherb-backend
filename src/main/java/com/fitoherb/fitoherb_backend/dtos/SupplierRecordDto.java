package com.fitoherb.fitoherb_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record SupplierRecordDto(@NotBlank String supplierName, MultipartFile image, @NotNull boolean isMaster) {
}
