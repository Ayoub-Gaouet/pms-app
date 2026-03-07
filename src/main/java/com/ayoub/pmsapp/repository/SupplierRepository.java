package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findBySupplierCategoryId(Long categoryId);
    List<Supplier> findByNameContains(String nom);
}
