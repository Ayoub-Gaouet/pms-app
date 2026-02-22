package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.entities.SupplierCategory;

import java.util.List;

public interface SupplierCategoryService {
    public List<SupplierCategory> getAllCategorys();
    public SupplierCategory findCategoryById(long id);
    public SupplierCategory saveCategory(SupplierCategory supplierCategory);
    public SupplierCategory updateCategory(SupplierCategory supplierCategory);
    public SupplierCategory deleteCategory(long id);

}
