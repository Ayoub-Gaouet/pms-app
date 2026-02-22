package com.ayoub.pmsapp.service.product;

import com.ayoub.pmsapp.entities.product.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryService categoryService;

    public CategoryServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Override
    public Category findCategoryById(long id) {
        return categoryService.findCategoryById(id);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryService.saveCategory(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryService.updateCategory(category);
    }

    @Override
    public void deleteCategory(long id) {
        categoryService.deleteCategory(id);
    }
}
