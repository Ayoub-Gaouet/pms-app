package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.ProductCategoryRequestDTO;
import com.ayoub.pmsapp.dto.ProductCategoryResponseDTO;
import com.ayoub.pmsapp.entities.ProductCategory;
import com.ayoub.pmsapp.repository.ProductCategoryRepository;
import com.ayoub.pmsapp.service.ProductCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository, ModelMapper modelMapper) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductCategoryResponseDTO> getAllCategories() {
        return productCategoryRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    public ProductCategoryResponseDTO findCategoryById(Long id) {
        ProductCategory category = productCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return convertEntityToDto(category);
    }

    @Override
    public ProductCategoryResponseDTO saveCategory(ProductCategoryRequestDTO productCategoryDTO) {
        ProductCategory category = convertDtoToEntity(productCategoryDTO);
        ProductCategory saved = productCategoryRepository.save(category);
        return convertEntityToDto(saved);
    }

    @Override
    public ProductCategoryResponseDTO updateCategory(Long id, ProductCategoryRequestDTO productCategoryDTO) {
        ProductCategory category = convertDtoToEntity(productCategoryDTO);
        category.setId(id);
        ProductCategory updated = productCategoryRepository.save(category);
        return convertEntityToDto(updated);
    }

    @Override
    public void deleteCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }

    @Override
    public ProductCategoryResponseDTO convertEntityToDto(ProductCategory productCategory) {
        ProductCategoryResponseDTO responseDTO = new ProductCategoryResponseDTO();
        responseDTO.setId(productCategory.getId());
        responseDTO.setNom(productCategory.getNom());
        responseDTO.setCreated_at(productCategory.getCreated_at());
        responseDTO.setUpdated_at(productCategory.getUpdated_at());
        return responseDTO;
    }

    @Override
    public ProductCategory convertDtoToEntity(ProductCategoryRequestDTO productCategoryDTO) {
        ProductCategory category = new ProductCategory();
        category.setNom(productCategoryDTO.getNom());
        return category;
    }
}
