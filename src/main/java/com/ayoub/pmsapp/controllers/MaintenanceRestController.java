package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.dto.MaintenanceRequestDTO;
import com.ayoub.pmsapp.dto.MaintenanceResponseDTO;
import com.ayoub.pmsapp.service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@CrossOrigin("*")
public class MaintenanceRestController {

    private final MaintenanceService maintenanceService;

    public MaintenanceRestController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceResponseDTO createMaintenance(@Valid @RequestBody MaintenanceRequestDTO dto) {
        return maintenanceService.createMaintenance(dto);
    }

    @GetMapping("/{id}")
    public MaintenanceResponseDTO getMaintenanceById(@PathVariable Long id) {
        return maintenanceService.getMaintenanceById(id);
    }

    @GetMapping
    public List<MaintenanceResponseDTO> getMaintenances(
            @RequestParam(required = false) Long machineId,
            @RequestParam(required = false) Long technicianId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (machineId != null) return maintenanceService.getMaintenancesByMachineId(machineId);
        if (technicianId != null) return maintenanceService.getMaintenancesByTechnicianId(technicianId);
        if (date != null) return maintenanceService.getMaintenancesByDate(date);

        return maintenanceService.getAllMaintenances();
    }

    @PutMapping("/{id}")
    public MaintenanceResponseDTO updateMaintenance(@PathVariable Long id,
                                                    @Valid @RequestBody MaintenanceRequestDTO dto) {
        return maintenanceService.updateMaintenance(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
    }
}

