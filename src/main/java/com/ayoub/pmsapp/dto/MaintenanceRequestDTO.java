package com.ayoub.pmsapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRequestDTO {

    @NotNull(message = "machineId is required")
    private Long machineId;

    @NotNull(message = "technicianId is required")
    private Long technicianId;

    @NotNull(message = "date is required")
    private LocalDate date;

    @NotNull(message = "type is required")
    private String type;
}

