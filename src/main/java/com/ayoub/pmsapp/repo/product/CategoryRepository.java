package com.ayoub.pmsapp.repo.product;

import com.ayoub.pmsapp.entities.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
