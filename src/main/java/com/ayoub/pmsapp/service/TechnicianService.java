package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.TechnicianRequestDTO;
import com.ayoub.pmsapp.dto.TechnicianResponseDTO;
import com.ayoub.pmsapp.entities.Technician;

import java.util.List;

public interface TechnicianService {

    TechnicianResponseDTO convertEntityToDto(Technician technician);

    Technician convertDtoToEntity(TechnicianRequestDTO dto);

    TechnicianResponseDTO createTechnician(TechnicianRequestDTO dto);

    TechnicianResponseDTO getTechnicianById(Long id);

    List<TechnicianResponseDTO> getAllTechnicians();

    List<TechnicianResponseDTO> getTechnicianBySkillId(Long skillId);

    List<TechnicianResponseDTO> getTechnicianByMachineId(Long machineId);

    TechnicianResponseDTO updateTechnician(Long id, TechnicianRequestDTO dto);

    void deleteTechnician(Long id);

    TechnicianResponseDTO assignToMachine(Long technicianId, Long machineId);

    TechnicianResponseDTO unassignFromMachine(Long technicianId);
    Technician findTechnicianById(Long id);
}
