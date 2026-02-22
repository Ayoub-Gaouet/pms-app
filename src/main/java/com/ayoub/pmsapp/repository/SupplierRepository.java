package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findFirstBySupplierCategory_Id(long id);
}
