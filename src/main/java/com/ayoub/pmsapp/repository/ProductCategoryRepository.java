package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
