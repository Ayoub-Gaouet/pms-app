package com.ayoub.pmsapp.dto;

import com.ayoub.pmsapp.entities.MachineState;
import jakarta.validation.constraints.NotBlank;
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
public class MachineRequestDTO {
    @NotBlank(message = "nom is required")
    private String nom;
    @NotNull(message = "etat is required")
    private MachineState etat;
    private LocalDate maintenanceProchaine;
}