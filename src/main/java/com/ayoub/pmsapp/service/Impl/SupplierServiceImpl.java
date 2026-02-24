package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.SupplierRequestDTO;
import com.ayoub.pmsapp.dto.SupplierResponseDTO;
import com.ayoub.pmsapp.entities.SupplierCategory;
import com.ayoub.pmsapp.entities.Supplier;
import com.ayoub.pmsapp.repository.SupplierCategoryRepository;
import com.ayoub.pmsapp.repository.SupplierRepository;
import com.ayoub.pmsapp.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    private final SupplierCategoryRepository supplierCategoryRepository;

    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierCategoryRepository supplierCategoryRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierCategoryRepository = supplierCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SupplierResponseDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public SupplierResponseDTO findSupplierById(Long id) {
        return convertEntityToDto(supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id)));
    }

    @Override
    public SupplierResponseDTO saveSupplier(SupplierRequestDTO supplierDTO) {
        Supplier supplier = convertDtoToEntity(supplierDTO);
        Supplier saved = supplierRepository.save(supplier);
        return convertEntityToDto(saved);
    }

    @Override
    public SupplierResponseDTO updateSupplier(Long id, SupplierRequestDTO supplierDTO) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        supplier.setName(supplierDTO.getName());
        supplier.setTax_number(supplierDTO.getTax_number());
        supplier.setTelephone_number(supplierDTO.getTelephone_number());
        supplier.setAddress(supplierDTO.getAddress());
        SupplierCategory supplierCategory = supplierCategoryRepository.findById(supplierDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + supplierDTO.getCategoryId()));
        supplier.setSupplierCategory(supplierCategory);
        Supplier saved = supplierRepository.save(supplier);
        return convertEntityToDto(saved);
    }

    @Override
    public Supplier convertDtoToEntity(SupplierRequestDTO dto) {
        Supplier supplier = modelMapper.map(dto, Supplier.class);
        if (dto.getCategoryId() != null) {
            SupplierCategory category = supplierCategoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
            supplier.setSupplierCategory(category);
        }
        return supplier;
    }

    @Override
    public SupplierResponseDTO convertEntityToDto(Supplier supplier) {
        SupplierResponseDTO supplierResponseDTO = modelMapper.map(supplier, SupplierResponseDTO.class);
        if (supplier.getSupplierCategory() != null) {
            supplierResponseDTO.setCategoryId(supplier.getSupplierCategory().getId());
            supplierResponseDTO.setCategoryName(supplier.getSupplierCategory().getName());
        }
        return supplierResponseDTO;
    }
}
