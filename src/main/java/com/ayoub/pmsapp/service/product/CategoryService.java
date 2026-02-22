package com.ayoub.pmsapp.service.product;

import com.ayoub.pmsapp.entities.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category findCategoryById(long id);
    Category saveCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(long id);
}
