package com.ayoub.pmsapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianRequestDTO {

    @NotBlank(message = "nom is required")
    private String nom;

    private Long skillId;

    private Long machineAssigneeId;
}
