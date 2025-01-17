package com.fitoherb.fitoherb_backend.controllers;

import com.fitoherb.fitoherb_backend.dtos.ProductRecordDto;
import com.fitoherb.fitoherb_backend.models.CategoryModel;
import com.fitoherb.fitoherb_backend.models.ProductModel;
import com.fitoherb.fitoherb_backend.models.SupplierModel;
import com.fitoherb.fitoherb_backend.repositories.CategoryRepository;
import com.fitoherb.fitoherb_backend.repositories.ProductRepository;
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
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @PostMapping("/products")
    public ResponseEntity<Object> postProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {

        Optional<CategoryModel> category = categoryRepository.findById(UUID.fromString(productRecordDto.productCategory()));
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        Optional<SupplierModel> supplier = supplierRepository.findById(UUID.fromString(productRecordDto.supplier()));
        if (supplier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        }

        Optional<ProductModel> product = productRepository.findByProductName(productRecordDto.productName());
        if(!product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists");
        }

        var productModel = new ProductModel();
        productModel.setProductName(productRecordDto.productName());
        productModel.setPrice_in_cents(productRecordDto.price_in_cents());
        productModel.setProductDescription(productRecordDto.productDescription());
        productModel.setProductImageUrl(productRecordDto.productImageUrl());
        productModel.setProductCategory(category.get());
        productModel.setSupplier(supplier.get());

        System.out.println(productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/products/name={productName}")
    public ResponseEntity<List<ProductModel>> getProductByName(@PathVariable("productName") String productName) {
        List<ProductModel> productModelList = productRepository.findByProductNameContainingIgnoreCase(productName);
        if(productModelList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productModelList);
    }

    @GetMapping("/products/category={categoryName}")
    public ResponseEntity<List<ProductModel>> getProductByCategory(@PathVariable("categoryName") String categoryName) {
        Optional<CategoryModel> category = categoryRepository.findByNameContainingIgnoreCase(categoryName);
        List<ProductModel> productModelList = productRepository.findByProductCategory(category.get());
        if(productModelList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productModelList);
    }

    @GetMapping("/products/supplier={supplierName}")
    public ResponseEntity<List<ProductModel>> getProductBySupplier(@PathVariable("supplierName") String supplierName) {
        Optional<SupplierModel> supplier = supplierRepository.findBySupplierNameContainingIgnoreCase(supplierName);
        List<ProductModel> productModelList = productRepository.findBySupplier(supplier.get());
        if(productModelList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productModelList);
    }

    @PutMapping("/products/{productName}")
    public ResponseEntity<Object> updateProduct(@PathVariable("productName") String productName, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> product = productRepository.findByProductName(productName);
        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        var productModel = product.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.ok(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        productRepository.delete(product.get());
        return ResponseEntity.ok().body("Deleted");
    }

    @DeleteMapping("/products")
    public ResponseEntity<Object> deleteAllProducts() {
        productRepository.deleteAll();
        return ResponseEntity.ok().body("Deleted");
    }
}
