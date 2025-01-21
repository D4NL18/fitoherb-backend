package com.fitoherb.fitoherb_backend.controllers;

import com.fitoherb.fitoherb_backend.dtos.SupplierRecordDto;
import com.fitoherb.fitoherb_backend.models.SupplierModel;
import com.fitoherb.fitoherb_backend.services.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/supplier")
    public ResponseEntity<Object> addSupplier(@RequestBody @Valid SupplierRecordDto supplierRecordDto) {
        return supplierService.addSupplier(supplierRecordDto);
    }

    @GetMapping("/supplier")
    public ResponseEntity<List<SupplierModel>> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/supplier/name={name}")
    public ResponseEntity<Object> getSupplierByName(@PathVariable String name) {
        return supplierService.getSupplierByName(name);
    }

    @GetMapping("/supplier/id={id}")
    public ResponseEntity<Object> getSupplierByName(@PathVariable UUID id) {
        return supplierService.getSupplierByName(id);
    }

    @PutMapping("/supplier/{id}")
    public ResponseEntity<Object> updateSupplier(@PathVariable UUID id, @RequestBody @Valid SupplierRecordDto supplierRecordDto) {
        return supplierService.updateSupplier(id, supplierRecordDto);
    }

    @DeleteMapping("/supplier/{id}")
    public ResponseEntity<Object> deleteSupplier(@PathVariable UUID id) {
        return supplierService.deleteSupplier(id);
    }

    @DeleteMapping("/supplier")
    public ResponseEntity<Object> deleteAllSuppliers() {
        return supplierService.deleteAllSuppliers();
    }
}
