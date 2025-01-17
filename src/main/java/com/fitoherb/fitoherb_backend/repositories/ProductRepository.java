package com.fitoherb.fitoherb_backend.repositories;

import com.fitoherb.fitoherb_backend.models.CategoryModel;
import com.fitoherb.fitoherb_backend.models.ProductModel;
import com.fitoherb.fitoherb_backend.models.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    Optional<ProductModel> findByProductName(String productName);

    List<ProductModel> findByProductNameContainingIgnoreCase(String productName);

    List<ProductModel> findByProductCategory(CategoryModel productCategory);

    List<ProductModel> findBySupplier(SupplierModel supplier);
}
