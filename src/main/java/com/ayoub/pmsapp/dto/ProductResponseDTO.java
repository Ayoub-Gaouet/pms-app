package com.ayoub.pmsapp.dto;

import com.ayoub.pmsapp.entities.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Integer stock;
    private ProductImage productImage;
    private Long categoryId;
    private Long supplierId;
}

