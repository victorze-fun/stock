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
import org.springframework.web.server.ResponseStatusException;

import com.victorze.stock.dto.CreateProductDTO;
import com.victorze.stock.dto.ProductDTO;
import com.victorze.stock.dto.converter.ProductDTOConterver;
import com.victorze.stock.errors.ProductNotFoundException;
import com.victorze.stock.models.Product;
import com.victorze.stock.repositories.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Product details that will appear in the UI")
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

    @Operation(summary = "Obtener un producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/{id}")
    public Product show(@PathVariable Long id) throws Exception {
        return productRepository.findById(id)
                // .orElseThrow(() -> new ProductNotFoundException(id));
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró el producto con id: " + id));
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProductDTO body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productRepository.save(productDTOConterver.convertToProduct(body)));
    }

    @PutMapping("/{id}")
    public Product update(@RequestBody CreateProductDTO body, @PathVariable Long id) {
        return productRepository.findById(id)
                .map(_p -> {
                    var product = productDTOConterver.convertToProduct(body);
                    product.setId(id);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
