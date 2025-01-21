package com.fitoherb.fitoherb_backend.controllers;

import com.fitoherb.fitoherb_backend.dtos.CategoryRecordDto;
import com.fitoherb.fitoherb_backend.models.CategoryModel;
import com.fitoherb.fitoherb_backend.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<CategoryModel> createCategory(@RequestBody @Valid CategoryRecordDto categoryRecordDto) {
        return categoryService.createCategory(categoryRecordDto);
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<Object> getCategory(@PathVariable String name) {
        return categoryService.getCategory(name);
    }

    @PutMapping("/category/{name}")
    public ResponseEntity<Object> updateCategory(@PathVariable String name, @RequestBody @Valid CategoryRecordDto categoryRecordDto) {
        return categoryService.updateCategory(name, categoryRecordDto);
    }

    @DeleteMapping("/category/{id}")
        public ResponseEntity<Object> deleteCategory(@PathVariable("id") UUID id){
        return categoryService.deleteCategory(id);
    }

    @DeleteMapping("/category")
    public ResponseEntity<Object> deleteAllCategories(){
        return categoryService.deleteAllCategories();
    }


}


