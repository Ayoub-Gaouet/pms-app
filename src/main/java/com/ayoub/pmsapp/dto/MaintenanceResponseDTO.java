package com.ayoub.pmsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceResponseDTO {

    private Long id;
    private Long machineId;
    private String machineNom;
    private Long technicianId;
    private String technicianNom;
    private LocalDate date;
    private String type;
}

