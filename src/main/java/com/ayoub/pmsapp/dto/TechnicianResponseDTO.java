package com.ayoub.pmsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianResponseDTO {

    private Long id;
    private String nom;
    private Long skillId;
    private String skillName;
    private Long machineAssigneeId;
    private String machineAssigneeNom;
}
