package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.ProductCategoryRequestDTO;
import com.ayoub.pmsapp.dto.ProductCategoryResponseDTO;
import com.ayoub.pmsapp.entities.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryResponseDTO> getAllCategories();
    ProductCategoryResponseDTO findCategoryById(Long id);
    ProductCategoryResponseDTO saveCategory(ProductCategoryRequestDTO productCategoryDTO);
    ProductCategoryResponseDTO updateCategory(Long id, ProductCategoryRequestDTO productCategoryDTO);
    void deleteCategory(Long id);
    ProductCategoryResponseDTO convertEntityToDto(ProductCategory productCategory);
    ProductCategory convertDtoToEntity(ProductCategoryRequestDTO productCategoryDTO);
}
