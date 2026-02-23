package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.entities.SupplierCategory;
import com.ayoub.pmsapp.service.SupplierCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/categories")
public class SupplierCategoryRestController {
    private final SupplierCategoryService supplierCategoryService;

    public SupplierCategoryRestController(SupplierCategoryService supplierCategoryService) {
        this.supplierCategoryService = supplierCategoryService;
    }

    @GetMapping("")
    public List<SupplierCategory> getAllCategories() {
        return supplierCategoryService.getAllCategorys();
    }

    @GetMapping("/{id}")
    public SupplierCategory findCategoryById(@PathVariable long id) {
        return supplierCategoryService.findCategoryById(id);
    }

    @PostMapping("")
    public SupplierCategory saveCategory(@RequestBody SupplierCategory supplierCategory) {
        return supplierCategoryService.saveCategory(supplierCategory);
    }

    @PutMapping("/{id}")
    public SupplierCategory updateCategory(@PathVariable long id, @RequestBody SupplierCategory supplierCategory) {
        supplierCategory.setId(id); // Assure que l'id est bien défini
        return supplierCategoryService.updateCategory(supplierCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SupplierCategory deleteCategory(@PathVariable long id) {
        return supplierCategoryService.deleteCategory(id);
    }

}
