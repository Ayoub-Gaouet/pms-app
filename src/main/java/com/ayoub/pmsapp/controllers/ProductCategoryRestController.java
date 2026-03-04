package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.entities.ProductCategory;
import com.ayoub.pmsapp.service.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@CrossOrigin("*")
public class ProductCategoryRestController {
    final ProductCategoryService productCategoryService;

    public ProductCategoryRestController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public List<ProductCategory> getAllCategories()
    {
        return productCategoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ProductCategory findCategoryById(@PathVariable("id") Long id) {
        return productCategoryService.findCategoryById(id);
    }

    @PostMapping
    public ProductCategory saveCategory(@RequestBody ProductCategory productCategory) {
        return productCategoryService.saveCategory(productCategory);
    }

    @PutMapping("/{id}")
    public ProductCategory updateCategory(@PathVariable Long id, @RequestBody ProductCategory productCategory) {
        productCategory.setId(id);
        return productCategoryService.updateCategory(productCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        productCategoryService.deleteCategory(id);
    }
}