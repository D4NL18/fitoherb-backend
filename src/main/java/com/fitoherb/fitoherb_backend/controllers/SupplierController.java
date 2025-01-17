package com.fitoherb.fitoherb_backend.controllers;

import com.fitoherb.fitoherb_backend.dtos.SupplierRecordDto;
import com.fitoherb.fitoherb_backend.models.CategoryModel;
import com.fitoherb.fitoherb_backend.models.SupplierModel;
import com.fitoherb.fitoherb_backend.repositories.SupplierRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @PostMapping("/supplier")
    public ResponseEntity<Object> addSupplier(@RequestBody @Valid SupplierRecordDto supplierRecordDto) {
        var supplierModel = new SupplierModel();
        BeanUtils.copyProperties(supplierRecordDto, supplierModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierRepository.save(supplierModel));
    }

    @GetMapping("/supplier")
    public ResponseEntity<List<SupplierModel>> getAllSuppliers() {
        List<SupplierModel> allSuppliers = supplierRepository.findAll();
        if(allSuppliers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(supplierRepository.findAll());
    }

    @GetMapping("/supplier/name={name}")
    public ResponseEntity<Object> getSupplierByName(@PathVariable String name) {
        var supplier = supplierRepository.findBySupplierNameIsIgnoreCase(name);
        if(!supplier.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if(supplier.get().getSupplierName() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(supplier);
    }

    @GetMapping("/supplier/id={id}")
    public ResponseEntity<Object> getSupplierByName(@PathVariable UUID id) {
        var supplier = supplierRepository.findById(id);
        if(!supplier.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if(supplier.get().getSupplierName() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(supplier);
    }

    @PutMapping("/supplier/{id}")
    public ResponseEntity<Object> updateSupplier(@PathVariable UUID id, @RequestBody @Valid SupplierRecordDto supplierRecordDto) {
        Optional<SupplierModel> supplier = supplierRepository.findById(id);
        if(supplier.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var supplierModel = new SupplierModel();
        BeanUtils.copyProperties(supplierRecordDto, supplierModel);
        return ResponseEntity.ok().body(supplierRepository.save(supplierModel));
    }

    @DeleteMapping("/supplier/{id}")
    public ResponseEntity<Object> deleteSupplier(@PathVariable UUID id) {
        Optional<SupplierModel> supplier = supplierRepository.findById(id);
        if(supplier.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        supplierRepository.delete(supplier.get());
        return ResponseEntity.ok("Deleted");
    }

    @DeleteMapping("/supplier")
    public ResponseEntity<Object> deleteAllSuppliers() {
        supplierRepository.deleteAll();
        return ResponseEntity.ok().body("Deleted");
    }
}
