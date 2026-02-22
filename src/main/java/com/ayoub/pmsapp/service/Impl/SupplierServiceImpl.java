package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.SupplierDTO;
import com.ayoub.pmsapp.entities.SupplierCategory;
import com.ayoub.pmsapp.entities.Supplier;
import com.ayoub.pmsapp.repository.SupplierCategoryRepository;
import com.ayoub.pmsapp.repository.SupplierRepository;
import com.ayoub.pmsapp.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierCategoryRepository supplierCategoryRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findSupplierById(long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
    }


    @Override
    public Supplier findSupplierByIdCategory(long id) {
        return supplierRepository.findFirstBySupplierCategory_Id(id);
    }

    @Override
    public Supplier saveSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setTax_number(supplierDTO.getTax_number());
        supplier.setTelephone_number(supplierDTO.getTelephone_number());
        supplier.setAddress(supplierDTO.getAddress());
        SupplierCategory supplierCategory = supplierCategoryRepository.findById(supplierDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + supplierDTO.getCategoryId()));
        supplier.setSupplierCategory(supplierCategory);
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(long id, SupplierDTO supplierDTO) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        supplier.setName(supplierDTO.getName());
        supplier.setTax_number(supplierDTO.getTax_number());
        supplier.setTelephone_number(supplierDTO.getTelephone_number());
        supplier.setAddress(supplierDTO.getAddress());
        SupplierCategory supplierCategory = supplierCategoryRepository.findById(supplierDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + supplierDTO.getCategoryId()));
        supplier.setSupplierCategory(supplierCategory);
        return supplierRepository.save(supplier);
    }
}
