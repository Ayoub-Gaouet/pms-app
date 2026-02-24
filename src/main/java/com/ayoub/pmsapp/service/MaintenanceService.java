package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.MaintenanceRequestDTO;
import com.ayoub.pmsapp.dto.MaintenanceResponseDTO;
import com.ayoub.pmsapp.entities.Maintenance;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceService {

    MaintenanceResponseDTO createMaintenance(MaintenanceRequestDTO dto);

    MaintenanceResponseDTO getMaintenanceById(Long id);
    Maintenance findMaintenanceById(Long id);

    List<MaintenanceResponseDTO> getAllMaintenances();

    List<MaintenanceResponseDTO> getMaintenancesByMachineId(Long machineId);

    List<MaintenanceResponseDTO> getMaintenancesByTechnicianId(Long technicianId);

    List<MaintenanceResponseDTO> getMaintenancesByDate(LocalDate date);

    MaintenanceResponseDTO updateMaintenance(Long id, MaintenanceRequestDTO dto);

    void deleteMaintenance(Long id);

    MaintenanceResponseDTO convertEntityToDto(Maintenance maintenance);

    Maintenance convertDtoToEntity(MaintenanceRequestDTO dto);
}

