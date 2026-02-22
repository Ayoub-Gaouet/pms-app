package com.ayoub.pmsapp.service.product;

import com.ayoub.pmsapp.dto.ProductDTO;
import com.ayoub.pmsapp.entities.product.Product;
import com.ayoub.pmsapp.repo.product.ImageRepository;
import com.ayoub.pmsapp.repo.product.ProductRepository;
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
                this.findProductById(productDTO.getId()).getImage().getId();
        Long newProdImageId = productDTO.getImage().getId();
        ProductDTO avUpdated = convertEntityToDto(productRepository.save(convertDtoToEntity(productDTO)));
        if (oldProdImageId != newProdImageId) //si l'image a été modifiée
            imageRepository.deleteById(oldProdImageId);
        return avUpdated;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Long oldProdImageId =
                this.findProductById(productDTO.getId()).getImage().getId();
        Long newProdImageId = productDTO.getImage().getId();
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
