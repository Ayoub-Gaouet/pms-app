package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.dto.TechnicianRequestDTO;
import com.ayoub.pmsapp.dto.TechnicianResponseDTO;
import com.ayoub.pmsapp.service.TechnicianService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technicians")
@CrossOrigin("*")
public class TechnicianRestController {

    private final TechnicianService technicianService;

    public TechnicianRestController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechnicianResponseDTO createTechnician(@Valid @RequestBody TechnicianRequestDTO dto) {
        return technicianService.createTechnician(dto);
    }

    @GetMapping("/{id}")
    public TechnicianResponseDTO getTechnicianById(@PathVariable Long id) {
        return technicianService.getTechnicianById(id);
    }

    @GetMapping
    public List<TechnicianResponseDTO> getAllTechnicians(
            @RequestParam(required = false) Long skillId,
            @RequestParam(required = false) Long machineId) {
        if (skillId != null) return technicianService.getTechnicianBySkillId(skillId);
        if (machineId != null) return technicianService.getTechnicianByMachineId(machineId);
        return technicianService.getAllTechnicians();
    }

    @PutMapping("/{id}")
    public TechnicianResponseDTO updateTechnician(@PathVariable Long id, @Valid @RequestBody TechnicianRequestDTO dto) {
        return technicianService.updateTechnician(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        technicianService.deleteTechnician(id);
    }

    @PutMapping("/{technicianId}/assign-machine/{machineId}")
    public TechnicianResponseDTO assignToMachine(
            @PathVariable Long technicianId,
            @PathVariable Long machineId) {
        return technicianService.assignToMachine(technicianId, machineId);
    }

    @PutMapping("/{technicianId}/unassign-machine")
    public TechnicianResponseDTO unassignFromMachine(@PathVariable Long technicianId) {
        return technicianService.unassignFromMachine(technicianId);
    }
}
