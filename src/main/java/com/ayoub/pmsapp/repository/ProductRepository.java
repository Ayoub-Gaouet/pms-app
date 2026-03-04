package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductCategoryId(Long categoryId);
}