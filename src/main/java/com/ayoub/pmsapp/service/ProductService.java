package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.ProductRequestDTO;
import com.ayoub.pmsapp.dto.ProductResponseDTO;
import com.ayoub.pmsapp.entities.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO saveProduct(ProductRequestDTO productDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productDTO);
    void deleteProduct(Long id);
    ProductResponseDTO findProductById(Long id);

    ProductResponseDTO convertEntityToDto (Product product);
    Product convertDtoToEntity(ProductRequestDTO productDTO);
}