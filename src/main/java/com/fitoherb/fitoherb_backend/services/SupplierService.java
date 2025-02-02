package com.fitoherb.fitoherb_backend.services;

import com.fitoherb.fitoherb_backend.dtos.SupplierRecordDto;
import com.fitoherb.fitoherb_backend.models.SupplierModel;
import com.fitoherb.fitoherb_backend.repositories.SupplierRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplierService {

    @Value("${path.supplierImages}")
    private String uploadDir;

    @Autowired
    private SupplierRepository supplierRepository;

    public ResponseEntity<Object> addSupplier(SupplierRecordDto supplierRecordDto) {
        MultipartFile image = supplierRecordDto.image();

        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Image file is required.");
        }

        String fileName = image.getOriginalFilename();
        String filePath = this.uploadDir + File.separator + fileName;
        String localPath = "images/supplierImages/" + fileName;

        try {

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File serverFile = new File(filePath);
            image.transferTo(serverFile);

            SupplierModel supplierModel = new SupplierModel();
            supplierModel.setSupplierName(supplierRecordDto.supplierName());
            supplierModel.setMaster(supplierRecordDto.isMaster());
            supplierModel.setImagePath(localPath);

            supplierRepository.save(supplierModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(supplierModel);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving file: " + e.getMessage());
        }
    }

    public ResponseEntity<List<SupplierModel>> getAllSuppliers() {
        List<SupplierModel> allSuppliers = supplierRepository.findAll();
        if(allSuppliers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(supplierRepository.findAll());
    }

    public ResponseEntity<Object> getSupplierByName(String name) {
        var supplier = supplierRepository.findBySupplierNameIsIgnoreCase(name);
        if(!supplier.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if(supplier.get().getSupplierName() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(supplier);
    }

    public ResponseEntity<Object> getSupplierByName(UUID id) {
        var supplier = supplierRepository.findById(id);
        if(!supplier.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if(supplier.get().getSupplierName() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(supplier);
    }

    public ResponseEntity<Object> updateSupplier(UUID id, SupplierRecordDto supplierRecordDto) {
        Optional<SupplierModel> supplier = supplierRepository.findById(id);
        if(supplier.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var supplierModel = new SupplierModel();
        BeanUtils.copyProperties(supplierRecordDto, supplierModel);
        return ResponseEntity.ok().body(supplierRepository.save(supplierModel));
    }

    public ResponseEntity<Object> deleteSupplier(UUID id) {
        Optional<SupplierModel> supplier = supplierRepository.findById(id);
        if(supplier.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        supplierRepository.delete(supplier.get());
        return ResponseEntity.ok("Deleted");
    }

    public ResponseEntity<Object> deleteAllSuppliers() {
        supplierRepository.deleteAll();
        return ResponseEntity.ok().body("Deleted");
    }
}
