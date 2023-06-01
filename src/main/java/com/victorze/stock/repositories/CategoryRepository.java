package com.victorze.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorze.stock.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
