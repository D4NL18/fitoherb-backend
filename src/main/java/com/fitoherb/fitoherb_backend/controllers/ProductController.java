package com.fitoherb.fitoherb_backend.controllers;

import com.fitoherb.fitoherb_backend.dtos.ProductEditDto;
import com.fitoherb.fitoherb_backend.dtos.ProductRecordDto;
import com.fitoherb.fitoherb_backend.models.CategoryModel;
import com.fitoherb.fitoherb_backend.models.ProductModel;
import com.fitoherb.fitoherb_backend.models.SupplierModel;
import com.fitoherb.fitoherb_backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Object> addProduct(@RequestParam("productName") String productName,
                                             @RequestParam("productImageUrl") MultipartFile productImageUrl,
                                             @RequestParam("price_in_cents") int price_in_cents,
                                             @RequestParam("productDescription") String productDescription,
                                             @RequestParam("productCategory") UUID productCategory,
                                             @RequestParam("supplier") UUID supplier) {
        ProductRecordDto productRecordDto = new ProductRecordDto(productName, price_in_cents, productDescription, productCategory.toString(), productImageUrl, supplier.toString());

        return productService.postProduct(productRecordDto);
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/name={productName}")
    public ResponseEntity<List<ProductModel>> getProductByName(@PathVariable("productName") String productName) {
        return productService.getProductByName(productName);
    }

    @GetMapping("/products/id={id}")
    public ResponseEntity<Optional<ProductModel>> getProductByName(@PathVariable("id") UUID id) {
        return productService.getProductById(id);
    }

    @GetMapping("/products/category={categoryName}")
    public ResponseEntity<List<ProductModel>> getProductByCategory(@PathVariable("categoryName") String categoryName) {
        return productService.getProductByCategory(categoryName);
    }

    @GetMapping("/products/supplier={supplierName}")
    public ResponseEntity<List<ProductModel>> getProductBySupplier(@PathVariable("supplierName") String supplierName) {
        return productService.getProductBySupplier(supplierName);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(
                                                @PathVariable UUID id,
                                                @RequestParam("productName") String productName,
                                                @RequestParam("productImageUrl") Optional<MultipartFile> productImageUrl,
                                                @RequestParam("price_in_cents") int price_in_cents,
                                                @RequestParam("productDescription") String productDescription,
                                                @RequestParam("productCategory") UUID productCategory,
                                                @RequestParam("supplier") UUID supplier) {
        ProductEditDto productRecordDto = new ProductEditDto(
                productName,
                price_in_cents,
                productDescription,
                productCategory.toString(),
                productImageUrl,
                supplier.toString()
        );
        return productService.updateProduct(id, productRecordDto);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable UUID id) {
        return productService.deleteProductById(id);
    }

    @DeleteMapping("/products")
    public ResponseEntity<Object> deleteAllProducts() {
        return productService.deleteAllProducts();
    }
}
