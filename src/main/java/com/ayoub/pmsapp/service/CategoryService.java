package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.entities.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategorys();
    public Category findCategoryById(long id);
    public Category saveCategory(Category category);
    public Category updateCategory(Category category);
    public Category deleteCategory(long id);

}
