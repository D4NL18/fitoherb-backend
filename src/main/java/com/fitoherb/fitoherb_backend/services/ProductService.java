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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Value("${path.productImages}")
    private String uploadDir;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public ResponseEntity<Object> postProduct(ProductRecordDto productRecordDto) {

        MultipartFile image = productRecordDto.productImageUrl();
        // Verifica se a categoria existe
        Optional<CategoryModel> category = categoryRepository.findById(UUID.fromString(productRecordDto.productCategory()));
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        // Verifica se o fornecedor existe
        Optional<SupplierModel> supplier = supplierRepository.findById(UUID.fromString(productRecordDto.supplier()));
        if (supplier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        }

        // Verifica se o produto já existe
        Optional<ProductModel> existingProduct = productRepository.findByProductName(productRecordDto.productName());
        if (existingProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists");
        }

        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Image file is required.");
        }

        String fileName = image.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.'));  // Extensão da imagem (ex: .png, .jpg)

        // Gerar uma chave aleatória (UUID) e anexar ao nome do arquivo
        String uniqueFileName = UUID.randomUUID().toString() + fileName + fileExtension;

        // Caminho do arquivo completo para salvar a imagem
        String filePath = this.uploadDir + File.separator + uniqueFileName;

        // Caminho relativo para salvar no banco de dados
        String localPath = "images/productImages/" + uniqueFileName;

        try {

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File serverFile = new File(filePath);
            image.transferTo(serverFile);

            ProductModel productModel = new ProductModel();
            productModel.setProductName(productRecordDto.productName());
            productModel.setProductCategory(category.get());
            productModel.setProductDescription(productRecordDto.productDescription());
            productModel.setSupplier(supplier.get());
            productModel.setPrice_in_cents(productRecordDto.price_in_cents());
            productModel.setProductImageUrl(localPath);

            productRepository.save(productModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(productModel);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving file: " + e.getMessage());
        }
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
