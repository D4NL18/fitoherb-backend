package com.fitoherb.fitoherb_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity

@Getter
@Setter

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

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierModel supplier;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryModel productCategory;
}
