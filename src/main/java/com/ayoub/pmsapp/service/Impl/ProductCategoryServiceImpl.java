package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.entities.ProductCategory;
import com.ayoub.pmsapp.repository.ProductCategoryRepository;
import com.ayoub.pmsapp.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory findCategoryById(long id) {
        return productCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public ProductCategory saveCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory updateCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteCategory(long id) {
        productCategoryRepository.deleteById(id);
    }
}
