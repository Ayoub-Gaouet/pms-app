package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.MachineRequestDTO;
import com.ayoub.pmsapp.dto.MachineResponseDTO;
import com.ayoub.pmsapp.entities.MachineState;

import java.util.List;

public interface MachineService {
    MachineResponseDTO createMachine(MachineRequestDTO dto);
    MachineResponseDTO getMachineById(Long id);
    List<MachineResponseDTO> getAllMachines();
    List<MachineResponseDTO> getMachineByEtat(MachineState etat);
    MachineResponseDTO updateMachine(Long id, MachineRequestDTO dto);
    void deleteMachine(Long id);
}
