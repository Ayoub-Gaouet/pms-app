package com.ayoub.pmsapp.repo.product;

import com.ayoub.pmsapp.entities.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image , Long> {
}
