package com.ayoub.pmsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequestDTO {
    private String name;
    private String tax_number;
    private String telephone_number;
    private String address;
    private Long categoryId;
}

