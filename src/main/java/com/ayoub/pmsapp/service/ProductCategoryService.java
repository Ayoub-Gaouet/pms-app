package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.entities.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();
    ProductCategory findCategoryById(long id);
    ProductCategory saveCategory(ProductCategory productCategory);
    ProductCategory updateCategory(ProductCategory productCategory);
    void deleteCategory(long id);
}
