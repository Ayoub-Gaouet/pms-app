package com.ayoub.pmsapp.controllers.supplier;

import com.ayoub.pmsapp.entities.supplier.Category;
import com.ayoub.pmsapp.service.supplier.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/categories")
public class CategoryRestController {
    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategorys();
    }

    @GetMapping("/{id}")
    public Category findCategoryById(@PathVariable long id) {
        return categoryService.findCategoryById(id);
    }

    @PostMapping("")
    public Category saveCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable long id, @RequestBody Category category) {
        category.setId(id); // Assure que l'id est bien défini
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public Category deleteCategory(@PathVariable long id) {
        return categoryService.deleteCategory(id);
    }

}
