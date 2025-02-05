package com.fitoherb.fitoherb_backend.services;

import com.fitoherb.fitoherb_backend.dtos.CategoryRecordDto;
import com.fitoherb.fitoherb_backend.models.CategoryModel;
import com.fitoherb.fitoherb_backend.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<CategoryModel> createCategory(CategoryRecordDto categoryRecordDto) {
        var categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryRecordDto, categoryModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(categoryModel));
    }

    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        List<CategoryModel> allCategories = categoryRepository.findAll();
        if(allCategories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    public ResponseEntity<Object> getCategory(UUID id) {
        var category = categoryRepository.findById(id);
        if(!category.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if(category.get().getName() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(category);
    }

    public ResponseEntity<Object> updateCategory(UUID id, CategoryRecordDto categoryRecordDto) {
        Optional<CategoryModel> category = categoryRepository.findById(id);
        Optional<CategoryModel> newCategoryInfo = categoryRepository.findByName(categoryRecordDto.name());

        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        } else if (!newCategoryInfo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category Already Exists");
        }

        CategoryModel categoryModel = category.get();
        BeanUtils.copyProperties(categoryRecordDto, categoryModel);
        return ResponseEntity.ok(categoryRepository.save(categoryModel));
    }

    public ResponseEntity<Object> deleteCategory(UUID id){
        Optional<CategoryModel> category = categoryRepository.findById(id);
        if(category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        categoryRepository.delete(category.get());
        return ResponseEntity.ok().body("Deleted");
    }

    public ResponseEntity<Object> deleteAllCategories(){
        categoryRepository.deleteAll();
        return ResponseEntity.ok().body("Deleted");
    }
}
