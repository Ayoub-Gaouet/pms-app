package com.ayoub.pmsapp.repo.supplier;

import com.ayoub.pmsapp.entities.supplier.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
