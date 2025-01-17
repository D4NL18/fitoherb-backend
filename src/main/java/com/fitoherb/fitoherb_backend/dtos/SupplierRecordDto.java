package com.fitoherb.fitoherb_backend.dtos;

import jakarta.validation.constraints.NotBlank;

public record SupplierRecordDto(@NotBlank String supplierName) {
}
