package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.ProductDTO;
import com.ayoub.pmsapp.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO findProductById(Long id);

    ProductDTO convertEntityToDto (Product product);
    Product convertDtoToEntity(ProductDTO productDTO);
}