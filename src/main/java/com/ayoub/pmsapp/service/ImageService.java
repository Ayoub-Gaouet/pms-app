package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.entities.ProductImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
public interface ImageService {
    ProductImage uplaodImage(MultipartFile file) throws IOException;
    ProductImage getImageDetails(Long id) throws IOException;
    ResponseEntity<byte[]> getImage(Long id) throws IOException;
    void deleteImage(Long id) ;
}
