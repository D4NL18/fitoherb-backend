package com.fitoherb.fitoherb_backend.repositories;

import com.fitoherb.fitoherb_backend.models.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierModel, UUID> {
    Optional<SupplierModel> findBySupplierName(String supplierName);

    Optional<SupplierModel> findBySupplierNameIsIgnoreCase(String name);
}
