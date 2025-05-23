package com.fitoherb.fitoherb_backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity

@Getter
@Setter

@Table(name = "TB_SUPPLIER")
public class SupplierModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID supplierId;
    private String supplierName;
    private String imagePath;
    private boolean isMaster;
}
