package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.entities.SupplierCategory;
import com.ayoub.pmsapp.repository.SupplierCategoryRepository;
import com.ayoub.pmsapp.service.SupplierCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierCategoryServiceImpl implements SupplierCategoryService {
    private final SupplierCategoryRepository supplierCategoryRepository;

    public SupplierCategoryServiceImpl(SupplierCategoryRepository supplierCategoryRepository) {
        this.supplierCategoryRepository = supplierCategoryRepository;
    }

    @Override
    public List<SupplierCategory> getAllCategorys() {
        return supplierCategoryRepository.findAll();
    }

    @Override
    public SupplierCategory findCategoryById(long id) {
        return supplierCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public SupplierCategory saveCategory(SupplierCategory supplierCategory) {
        return supplierCategoryRepository.save(supplierCategory);
    }

    @Override
    public SupplierCategory updateCategory(SupplierCategory supplierCategory) {
        return supplierCategoryRepository.save(supplierCategory);
    }

    @Override
    public SupplierCategory deleteCategory(long id) {
        SupplierCategory supplierCategory = supplierCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        supplierCategoryRepository.delete(supplierCategory);
        return supplierCategory;
    }

}
