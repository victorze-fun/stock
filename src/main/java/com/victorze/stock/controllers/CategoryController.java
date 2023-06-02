package com.victorze.stock.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorze.stock.errors.CategoryNotFoundException;
import com.victorze.stock.models.Category;
import com.victorze.stock.repositories.CategoryRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping()
    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PostMapping()
    public ResponseEntity<Category> newCategory(@RequestBody Category body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryRepository.save(body));
    }

    @PutMapping("/{id}")
    public Category editCategory(@PathVariable Long id, @RequestBody Category body) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(body.getName());
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
