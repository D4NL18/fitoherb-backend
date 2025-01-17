package com.fitoherb.fitoherb_backend.repositories;

import com.fitoherb.fitoherb_backend.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
    Optional<CategoryModel> findByName(String name);

    Optional<CategoryModel> findByNameContainingIgnoreCase(String name);
}
