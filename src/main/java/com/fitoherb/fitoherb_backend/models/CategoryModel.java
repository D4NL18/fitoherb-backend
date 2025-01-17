package com.fitoherb.fitoherb_backend.models;

import jakarta.persistence.*;


import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_CATEGORY")
public class CategoryModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCategory;
    private String name;

    public UUID getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(UUID idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
