package com.ayoub.pmsapp.service.product;

import com.ayoub.pmsapp.dto.ProductDTO;
import com.ayoub.pmsapp.entities.product.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO findProductById(Long id);

    ProductDTO convertEntityToDto (Product avions);
    Product convertDtoToEntity(ProductDTO avionDTO);
}