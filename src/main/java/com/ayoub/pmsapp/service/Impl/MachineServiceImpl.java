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
    public MachineResponseDTO convertEntityToDto(Machine machine) {
        return new MachineResponseDTO(
                machine.getId(),
                machine.getNom(),
                machine.getEtat(),
                machine.getMaintenanceProchaine()
        );
    }

    @Override
    public Machine convertDtoToEntity(MachineRequestDTO dto) {
        Machine m = new Machine();
        m.setNom(dto.getNom());
        m.setEtat(dto.getEtat());
        m.setMaintenanceProchaine(dto.getMaintenanceProchaine());
        return m;
    }

    @Override
    public MachineResponseDTO createMachine(MachineRequestDTO dto) {
        Machine m = convertDtoToEntity(dto);
        Machine saved = machineRepository.save(m);
        return convertEntityToDto(saved);
    }

    @Override
    public MachineResponseDTO getMachineById(Long id) {
        return convertEntityToDto(findOrThrow(id));
    }

    @Override
    public List<MachineResponseDTO> getAllMachines() {
        return machineRepository.findAll().stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public List<MachineResponseDTO> getMachineByEtat(MachineState etat) {
        return machineRepository.findByEtat(etat).stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public MachineResponseDTO updateMachine(Long id, MachineRequestDTO dto) {
        Machine m = findOrThrow(id);
        Machine fromDto = convertDtoToEntity(dto);
        m.setNom(fromDto.getNom());
        m.setEtat(fromDto.getEtat());
        m.setMaintenanceProchaine(fromDto.getMaintenanceProchaine());
        Machine saved = machineRepository.save(m);
        return convertEntityToDto(saved);
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
}
