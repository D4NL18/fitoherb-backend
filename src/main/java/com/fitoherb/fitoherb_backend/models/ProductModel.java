package com.fitoherb.fitoherb_backend.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_PRODUCTS")
public class ProductModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduct;
    private String productName;
    private int price_in_cents;
    private String productDescription;
    private String productImageUrl;
    private String supplier;
    @ManyToOne
    @JoinColumn(name = "product_category_id_category")
    private CategoryModel productCategory;

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice_in_cents() {
        return price_in_cents;
    }

    public void setPrice_in_cents(int price_in_cents) {
        this.price_in_cents = price_in_cents;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public CategoryModel getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(CategoryModel productCategory) {
        this.productCategory = productCategory;
    }
}
