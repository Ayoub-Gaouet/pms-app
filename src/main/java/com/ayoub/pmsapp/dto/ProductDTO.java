package com.ayoub.pmsapp.dto;

import com.ayoub.pmsapp.entities.product.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private Image image;
    private Long categoryId;
    private Long supplierId;
}
