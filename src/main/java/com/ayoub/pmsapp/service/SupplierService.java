package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.SupplierRequestDTO;
import com.ayoub.pmsapp.dto.SupplierResponseDTO;
import com.ayoub.pmsapp.entities.Supplier;

import java.util.List;

public interface SupplierService {
    List<SupplierResponseDTO> getAllSuppliers();

    SupplierResponseDTO findSupplierById(Long id);

    SupplierResponseDTO saveSupplier(SupplierRequestDTO supplierDTO);

    SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierDTO);

    Supplier convertDtoToEntity(SupplierRequestDTO supplierDTO);

    SupplierResponseDTO convertEntityToDto(Supplier supplier);
}
