package com.ayoub.pmsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private String name;
    private Integer stock;
    private Long productImageId;
    private Long categoryId;
    private Long supplierId;
}

