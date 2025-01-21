package com.fitoherb.fitoherb_backend.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_SUPPLIER")
public class SupplierModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID supplierId;
    private String supplierName;
    private String imagePath;
    private boolean isMaster;

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
