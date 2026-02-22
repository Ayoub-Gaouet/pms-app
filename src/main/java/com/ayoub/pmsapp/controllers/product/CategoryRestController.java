package com.ayoub.pmsapp.controllers.product;

import com.ayoub.pmsapp.entities.product.Category;
import com.ayoub.pmsapp.service.product.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@CrossOrigin("*")
public class CategoryRestController {
    final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method= RequestMethod.GET)
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Category findCategoryById(@PathVariable("id") Long id) {
        return categoryService.findCategoryById(id);
    }

}