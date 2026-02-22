package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}