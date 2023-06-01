package com.victorze.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorze.stock.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
