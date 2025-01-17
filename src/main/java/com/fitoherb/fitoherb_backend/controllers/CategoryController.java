package com.fitoherb.fitoherb_backend.controllers;


import com.fitoherb.fitoherb_backend.dtos.CategoryRecordDto;
import com.fitoherb.fitoherb_backend.models.CategoryModel;
import com.fitoherb.fitoherb_backend.repositories.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/category")
    public ResponseEntity<CategoryModel> createCategory(@RequestBody @Valid CategoryRecordDto categoryRecordDto) {
        var categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryRecordDto, categoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(categoryModel));
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        List<CategoryModel> allCategories = categoryRepository.findAll();
        if(allCategories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<Object> getCategory(@PathVariable String name) {
        var category = categoryRepository.findByName(name);
        if(!category.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if(category.get().getName() == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println(category.get().getName());
        return ResponseEntity.ok().body(category);
    }

    @PutMapping("/category/{name}")
    public ResponseEntity<Object> updateCategory(@PathVariable String name, @RequestBody @Valid CategoryRecordDto categoryRecordDto) {
        Optional<CategoryModel> category = categoryRepository.findByName(name);
        Optional<CategoryModel> newCategoryInfo = categoryRepository.findByName(categoryRecordDto.name());
        if(category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }else if(!newCategoryInfo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category Already Exists");
        }
        var categoryModel = category.get();
        BeanUtils.copyProperties(categoryRecordDto, categoryModel);
        return ResponseEntity.ok(categoryRepository.save(categoryModel));
    }

    @DeleteMapping("/category/{name}")
        public ResponseEntity<Object> deleteCategory(@PathVariable("name") String name){
        Optional<CategoryModel> category = categoryRepository.findByName(name);
        if(category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        categoryRepository.delete(category.get());
        return ResponseEntity.ok().body("Deleted");
    }

    @DeleteMapping("/category")
    public ResponseEntity<Object> deleteAllCategories(){
        categoryRepository.deleteAll();
        return ResponseEntity.ok().body("Deleted");
    }


}


