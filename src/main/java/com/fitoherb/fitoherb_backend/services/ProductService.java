package com.fitoherb.fitoherb_backend.services;

import com.fitoherb.fitoherb_backend.dtos.ProductRecordDto;
import com.fitoherb.fitoherb_backend.models.CategoryModel;
import com.fitoherb.fitoherb_backend.models.ProductModel;
import com.fitoherb.fitoherb_backend.models.SupplierModel;
import com.fitoherb.fitoherb_backend.repositories.CategoryRepository;
import com.fitoherb.fitoherb_backend.repositories.ProductRepository;
import com.fitoherb.fitoherb_backend.repositories.SupplierRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public ResponseEntity<Object> postProduct(ProductRecordDto productRecordDto) {

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


    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    public ResponseEntity<List<ProductModel>> getProductByName(String productName) {
        List<ProductModel> productModelList = productRepository.findByProductNameContainingIgnoreCase(productName);
        if(productModelList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productModelList);
    }

    public ResponseEntity<List<ProductModel>> getProductByCategory(String categoryName) {
        Optional<CategoryModel> category = categoryRepository.findByNameContainingIgnoreCase(categoryName);
        List<ProductModel> productModelList = productRepository.findByProductCategory(category.get());
        if(productModelList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productModelList);
    }

    public ResponseEntity<List<ProductModel>> getProductBySupplier(String supplierName) {
        Optional<SupplierModel> supplier = supplierRepository.findBySupplierNameContainingIgnoreCase(supplierName);
        List<ProductModel> productModelList = productRepository.findBySupplier(supplier.get());
        if(productModelList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productModelList);
    }

    public ResponseEntity<Object> updateProduct(String productName, ProductRecordDto productRecordDto) {
        Optional<ProductModel> product = productRepository.findByProductName(productName);
        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        var productModel = product.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.ok(productRepository.save(productModel));
    }

    public ResponseEntity<Object> deleteProductById(UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if(product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        productRepository.delete(product.get());
        return ResponseEntity.ok().body("Deleted");
    }

    public ResponseEntity<Object> deleteAllProducts() {
        productRepository.deleteAll();
        return ResponseEntity.ok().body("Deleted");
    }
}
