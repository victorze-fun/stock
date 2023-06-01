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

import com.victorze.stock.dto.CreateProductDTO;
import com.victorze.stock.dto.ProductDTO;
import com.victorze.stock.dto.converter.ProductDTOConterver;
import com.victorze.stock.models.Product;
import com.victorze.stock.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductDTOConterver productDTOConterver;

    @GetMapping
    public Iterable<ProductDTO> index() {
        return productRepository.findAll()
                .stream()
                .map(productDTOConterver::convertToDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProductDTO body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productRepository.save(productDTOConterver.convertToProduct(body)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CreateProductDTO body, @PathVariable Long id) {
        return productRepository.findById(id)
                .map(_p -> {
                    var product = productDTOConterver.convertToProduct(body);
                    product.setId(id);
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
