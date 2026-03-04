package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.dto.ProductCategoryRequestDTO;
import com.ayoub.pmsapp.dto.ProductCategoryResponseDTO;
import com.ayoub.pmsapp.service.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@CrossOrigin("*")
public class ProductCategoryRestController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryRestController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public List<ProductCategoryResponseDTO> getAllCategories() {
        return productCategoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ProductCategoryResponseDTO findCategoryById(@PathVariable("id") Long id) {
        return productCategoryService.findCategoryById(id);
    }

    @PostMapping
    public ProductCategoryResponseDTO saveCategory(@Valid @RequestBody ProductCategoryRequestDTO productCategoryDTO) {
        return productCategoryService.saveCategory(productCategoryDTO);
    }

    @PutMapping("/{id}")
    public ProductCategoryResponseDTO updateCategory(@PathVariable Long id, @Valid @RequestBody ProductCategoryRequestDTO productCategoryDTO) {
        return productCategoryService.updateCategory(id, productCategoryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        productCategoryService.deleteCategory(id);
    }
}