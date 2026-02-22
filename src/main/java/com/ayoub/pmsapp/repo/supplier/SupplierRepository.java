package com.ayoub.pmsapp.repo.supplier;

import com.ayoub.pmsapp.entities.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByCategoryId(long id);
}
