package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.SupplierDTO;
import com.ayoub.pmsapp.entities.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAllSuppliers();

    Supplier findSupplierById(Long id);

    Supplier saveSupplier(SupplierDTO supplierDTO);

    Supplier updateSupplier(Long id, SupplierDTO supplierDTO);
}
