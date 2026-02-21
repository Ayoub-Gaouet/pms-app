package com.ayoub.pmsapp.repo;

import com.ayoub.pmsapp.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByCategoryId(long id);
}
