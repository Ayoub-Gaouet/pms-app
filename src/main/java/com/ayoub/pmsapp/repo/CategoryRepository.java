package com.ayoub.pmsapp.repo;

import com.ayoub.pmsapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
