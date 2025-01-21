package com.fitoherb.fitoherb_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record SupplierRecordDto(@NotBlank String supplierName, @NotNull MultipartFile image, @NotNull boolean isMaster) {
}
