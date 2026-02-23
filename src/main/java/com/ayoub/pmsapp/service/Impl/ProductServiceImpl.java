package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.ProductDTO;
import com.ayoub.pmsapp.entities.Product;
import com.ayoub.pmsapp.entities.ProductCategory;
import com.ayoub.pmsapp.entities.Supplier;
import com.ayoub.pmsapp.repository.ImageRepository;
import com.ayoub.pmsapp.repository.ProductCategoryRepository;
import com.ayoub.pmsapp.repository.ProductRepository;
import com.ayoub.pmsapp.repository.SupplierRepository;
import com.ayoub.pmsapp.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ImageRepository imageRepository,
                              ProductCategoryRepository productCategoryRepository, SupplierRepository supplierRepository,
                              ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertDtoToEntity(productDTO);
        if (productDTO.getId() != null) {
            Product existing = productRepository.findById(productDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productDTO.getId()));
            if (existing.getProductImage() != null && productDTO.getProductImage() != null) {
                Long oldProdImageId = existing.getProductImage().getId();
                Long newProdImageId = productDTO.getProductImage().getId();
                if (!oldProdImageId.equals(newProdImageId)) {
                    imageRepository.deleteById(oldProdImageId);
                }
            }
        }
        Product saved = productRepository.save(product);
        return convertEntityToDto(saved);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        if (productDTO.getId() == null) {
            throw new RuntimeException("Product id is required for update");
        }
        Product existing = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productDTO.getId()));
        if (existing.getProductImage() != null && productDTO.getProductImage() != null) {
            Long oldProdImageId = existing.getProductImage().getId();
            Long newProdImageId = productDTO.getProductImage().getId();
            if (!oldProdImageId.equals(newProdImageId)) {
                imageRepository.deleteById(oldProdImageId);
            }
        }
        Product product = convertDtoToEntity(productDTO);
        Product saved = productRepository.save(product);
        return convertEntityToDto(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO findProductById(Long id) {
        return convertEntityToDto(productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id)));
    }


    @Override
    public ProductDTO convertEntityToDto(Product product) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        if (product.getProductCategory() != null) {
            productDTO.setCategoryId(product.getProductCategory().getId());
        }
        if (product.getSupplier() != null) {
            productDTO.setSupplierId(product.getSupplier().getId());
        }
        return productDTO;
    }

    @Override
    public Product convertDtoToEntity(ProductDTO productDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Product product = modelMapper.map(productDTO, Product.class);
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
