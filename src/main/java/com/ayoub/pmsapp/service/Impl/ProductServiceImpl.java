package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.ProductDTO;
import com.ayoub.pmsapp.entities.Product;
import com.ayoub.pmsapp.repository.ImageRepository;
import com.ayoub.pmsapp.repository.ProductRepository;
import com.ayoub.pmsapp.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ImageRepository imageRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Long oldProdImageId =
                this.findProductById(productDTO.getId()).getProductImage().getId();
        Long newProdImageId = productDTO.getProductImage().getId();
        ProductDTO avUpdated = convertEntityToDto(productRepository.save(convertDtoToEntity(productDTO)));
        if (oldProdImageId != newProdImageId) //si l'image a été modifiée
            imageRepository.deleteById(oldProdImageId);
        return avUpdated;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Long oldProdImageId =
                this.findProductById(productDTO.getId()).getProductImage().getId();
        Long newProdImageId = productDTO.getProductImage().getId();
        ProductDTO avUpdated = convertEntityToDto(productRepository.save(convertDtoToEntity(productDTO)));
        if (oldProdImageId != newProdImageId) //si l'image a été modifiée
            imageRepository.deleteById(oldProdImageId);
        return avUpdated;
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
        return productDTO;
    }

    @Override
    public Product convertDtoToEntity(ProductDTO avionDTO) {
        Product product = new Product();
        product = modelMapper.map(avionDTO, Product.class);
        return product;
    }
}
