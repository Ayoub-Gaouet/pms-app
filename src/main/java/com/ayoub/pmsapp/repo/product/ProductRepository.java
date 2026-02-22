package com.ayoub.pmsapp.repo.product;

import com.ayoub.pmsapp.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}