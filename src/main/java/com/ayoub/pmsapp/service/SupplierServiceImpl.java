package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.SupplierDTO;
import com.ayoub.pmsapp.entities.Category;
import com.ayoub.pmsapp.entities.Supplier;
import com.ayoub.pmsapp.repo.CategoryRepository;
import com.ayoub.pmsapp.repo.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private SupplierRepository supplierRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        return supplierRepository.findByCategoryId(id);
    }

    @Override
    public Supplier saveSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setTax_number(supplierDTO.getTax_number());
        supplier.setTelephone_number(supplierDTO.getTelephone_number());
        supplier.setAddress(supplierDTO.getAddress());
        Category category = categoryRepository.findById(supplierDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + supplierDTO.getCategoryId()));
        supplier.setCategory(category);
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
        Category category = categoryRepository.findById(supplierDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + supplierDTO.getCategoryId()));
        supplier.setCategory(category);
        return supplierRepository.save(supplier);
    }
}
