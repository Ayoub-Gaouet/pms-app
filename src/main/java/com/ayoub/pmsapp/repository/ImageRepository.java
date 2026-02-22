package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {
}
