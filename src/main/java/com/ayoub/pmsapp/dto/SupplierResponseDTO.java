package com.ayoub.pmsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseDTO {
    private Long id;
    private String name;
    private String tax_number;
    private String telephone_number;
    private String address;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

