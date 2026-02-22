package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.dto.MachineRequestDTO;
import com.ayoub.pmsapp.dto.MachineResponseDTO;
import com.ayoub.pmsapp.entities.MachineState;
import com.ayoub.pmsapp.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@CrossOrigin("*")
public class MachineRestController {

    private final MachineService machineService;

    public MachineRestController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping
    public MachineResponseDTO createMachine(@Valid @RequestBody MachineRequestDTO dto) {
        return machineService.createMachine(dto);
    }

    @GetMapping("/{id}")
    public MachineResponseDTO getMachineById(@PathVariable Long id) {
        return machineService.getMachineById(id);
    }

    @GetMapping
    public List<MachineResponseDTO> getAllMachines(@RequestParam(required = false) MachineState etat) {
        if (etat != null) return machineService.getMachineByEtat(etat);
        return machineService.getAllMachines();
    }

    @PutMapping("/{id}")
    public MachineResponseDTO updateMachine(@PathVariable Long id, @Valid @RequestBody MachineRequestDTO dto) {
        return machineService.updateMachine(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteMachine(@PathVariable Long id) {
        machineService.deleteMachine(id);
    }
}