package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.MachineRequestDTO;
import com.ayoub.pmsapp.dto.MachineResponseDTO;
import com.ayoub.pmsapp.entities.Machine;
import com.ayoub.pmsapp.entities.MachineState;
import com.ayoub.pmsapp.entities.Technician;
import com.ayoub.pmsapp.exception.ResourceNotFoundException;
import com.ayoub.pmsapp.repository.MachineRepository;
import com.ayoub.pmsapp.repository.TechnicianRepository;
import com.ayoub.pmsapp.service.MachineService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;
    private final TechnicianRepository technicianRepository;

    public MachineServiceImpl(MachineRepository machineRepository, TechnicianRepository technicianRepository) {
        this.machineRepository = machineRepository;
        this.technicianRepository = technicianRepository;
    }
    @Override
    public MachineResponseDTO createMachine(MachineRequestDTO dto) {
        Machine m = new Machine();
        m.setNom(dto.getNom());
        m.setEtat(dto.getEtat());
        m.setMaintenanceProchaine(dto.getMaintenanceProchaine());
        Machine saved = machineRepository.save(m);
        return toDto(saved);    }

    @Override
    public MachineResponseDTO getMachineById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    public List<MachineResponseDTO> getAllMachines() {
        return machineRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<MachineResponseDTO> getMachineByEtat(MachineState etat) {
        return machineRepository.findByEtat(etat).stream().map(this::toDto).toList();
    }

    @Override
    public MachineResponseDTO updateMachine(Long id, MachineRequestDTO dto) {
        Machine m = findOrThrow(id);
        m.setNom(dto.getNom());
        m.setEtat(dto.getEtat());
        m.setMaintenanceProchaine(dto.getMaintenanceProchaine());
        return toDto(machineRepository.save(m));
    }

    @Override
    public void deleteMachine(Long id) {
        Machine m = findOrThrow(id);
        List<Technician> assigned = technicianRepository.findByMachineAssigneeId(id);
        for (Technician t : assigned) {
            t.setMachineAssignee(null);
        }
        technicianRepository.saveAll(assigned);
        machineRepository.delete(m);
    }
    private Machine findOrThrow(Long id) {
        return machineRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Machine not found with id=" + id));
    }
    private MachineResponseDTO toDto(Machine m) {
        return new MachineResponseDTO(m.getId(), m.getNom(), m.getEtat(), m.getMaintenanceProchaine());
    }
}
