package com.ayoub.pmsapp.service.supplier;

import com.ayoub.pmsapp.dto.SupplierDTO;
import com.ayoub.pmsapp.entities.supplier.Supplier;

import java.util.List;

public interface SupplierService {
     List<Supplier> getAllSuppliers();

     Supplier findSupplierById(long id);
     Supplier findSupplierByIdCategory(long id);

    Supplier saveSupplier(SupplierDTO supplierDTO);
    Supplier updateSupplier(long id, SupplierDTO supplierDTO);
}
