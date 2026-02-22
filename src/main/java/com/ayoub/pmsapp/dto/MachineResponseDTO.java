package com.ayoub.pmsapp.dto;

import com.ayoub.pmsapp.entities.MachineState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MachineResponseDTO {
    private Long id;
    private String nom;
    private MachineState etat;
    private LocalDate maintenanceProchaine;
}
