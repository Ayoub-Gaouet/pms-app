package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.ProductRequestDTO;
import com.ayoub.pmsapp.dto.ProductResponseDTO;
import com.ayoub.pmsapp.entities.Product;
import com.ayoub.pmsapp.entities.ProductCategory;
import com.ayoub.pmsapp.entities.Supplier;
import com.ayoub.pmsapp.repository.ProductCategoryRepository;
import com.ayoub.pmsapp.repository.ProductRepository;
import com.ayoub.pmsapp.repository.SupplierRepository;
import com.ayoub.pmsapp.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductCategoryRepository productCategoryRepository, SupplierRepository supplierRepository,
                              ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public ProductResponseDTO saveProduct(ProductRequestDTO productDTO) {
        Product product = convertDtoToEntity(productDTO);
        Product saved = productRepository.save(product);
        return convertEntityToDto(saved);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productDTO) {
        Product updated = convertDtoToEntity(productDTO);
        updated.setId(id);
        Product saved = productRepository.save(updated);
        return convertEntityToDto(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDTO findProductById(Long id) {
        return convertEntityToDto(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id)));
    }

    @Override
    public List<ProductResponseDTO> findProductsByCategory(Long categoryId) {
        return productRepository.findByProductCategoryId(categoryId).stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public List<ProductResponseDTO> findByNomProduitContains(String nom) {
        return productRepository.findByNameContains(nom).stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public ProductResponseDTO convertEntityToDto(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setStock(product.getStock());
        if (product.getProductCategory() != null) {
            productResponseDTO.setCategoryId(product.getProductCategory().getId());
            productResponseDTO.setCategoryName(product.getProductCategory().getNom());
        }
        if (product.getSupplier() != null) {
            productResponseDTO.setSupplierId(product.getSupplier().getId());
            productResponseDTO.setSupplierName(product.getSupplier().getName());
        }
        productResponseDTO.setCreated_at(product.getCreated_at());
        productResponseDTO.setUpdated_at(product.getUpdated_at());
        return productResponseDTO;
    }

    @Override
    public Product convertDtoToEntity(ProductRequestDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setStock(productDTO.getStock());
        if (productDTO.getCategoryId() != null) {
            ProductCategory category = productCategoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDTO.getCategoryId()));
            product.setProductCategory(category);
        }
        if (productDTO.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + productDTO.getSupplierId()));
            product.setSupplier(supplier);
        }
        return product;
    }
}
