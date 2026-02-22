package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.TechnicianRequestDTO;
import com.ayoub.pmsapp.dto.TechnicianResponseDTO;
import com.ayoub.pmsapp.entities.Machine;
import com.ayoub.pmsapp.entities.Skill;
import com.ayoub.pmsapp.entities.Technician;
import com.ayoub.pmsapp.exception.ResourceNotFoundException;
import com.ayoub.pmsapp.repository.MachineRepository;
import com.ayoub.pmsapp.repository.SkillRepository;
import com.ayoub.pmsapp.repository.TechnicianRepository;
import com.ayoub.pmsapp.service.TechnicianService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TechnicianServiceImpl implements TechnicianService {

    private final TechnicianRepository technicianRepository;
    private final SkillRepository skillRepository;
    private final MachineRepository machineRepository;

    public TechnicianServiceImpl(TechnicianRepository technicianRepository,
                                 SkillRepository skillRepository,
                                 MachineRepository machineRepository) {
        this.technicianRepository = technicianRepository;
        this.skillRepository = skillRepository;
        this.machineRepository = machineRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public TechnicianResponseDTO convertEntityToDto(Technician technician) {
        return new TechnicianResponseDTO(
                technician.getId(),
                technician.getNom(),
                technician.getSkill() != null ? technician.getSkill().getId() : null,
                technician.getSkill() != null ? technician.getSkill().getName() : null,
                technician.getMachineAssignee() != null ? technician.getMachineAssignee().getId() : null,
                technician.getMachineAssignee() != null ? technician.getMachineAssignee().getNom() : null
        );
    }

    @Override
    public Technician convertDtoToEntity(TechnicianRequestDTO dto) {
        Technician t = new Technician();
        t.setNom(dto.getNom());
        setSkillIfPresent(t, dto.getSkillId());
        setMachineIfPresent(t, dto.getMachineAssigneeId());
        return t;
    }

    @Override
    @Transactional
    public TechnicianResponseDTO createTechnician(TechnicianRequestDTO dto) {
        Technician t = convertDtoToEntity(dto);
        Technician saved = technicianRepository.save(t);
        return convertEntityToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TechnicianResponseDTO getTechnicianById(Long id) {
        return convertEntityToDto(findTechnicianOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TechnicianResponseDTO> getAllTechnicians() {
        return technicianRepository.findAll().stream().map(this::convertEntityToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TechnicianResponseDTO> getTechnicianBySkillId(Long skillId) {
        return technicianRepository.findBySkillId(skillId).stream().map(this::convertEntityToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TechnicianResponseDTO> getTechnicianByMachineId(Long machineId) {
        return technicianRepository.findByMachineAssigneeId(machineId).stream().map(this::convertEntityToDto).toList();
    }

    @Override
    @Transactional
    public TechnicianResponseDTO updateTechnician(Long id, TechnicianRequestDTO dto) {
        Technician existing = findTechnicianOrThrow(id);
        Technician fromDto = convertDtoToEntity(dto);
        existing.setNom(fromDto.getNom());
        existing.setSkill(fromDto.getSkill());
        existing.setMachineAssignee(fromDto.getMachineAssignee());
        Technician saved = technicianRepository.save(existing);
        return convertEntityToDto(saved);
    }

    @Override
    @Transactional
    public void deleteTechnician(Long id) {
        Technician t = findTechnicianOrThrow(id);
        technicianRepository.delete(t);
    }

    @Override
    @Transactional
    public TechnicianResponseDTO assignToMachine(Long technicianId, Long machineId) {
        Technician t = findTechnicianOrThrow(technicianId);
        Machine m = findMachineOrThrow(machineId);
        t.setMachineAssignee(m);
        Technician saved = technicianRepository.save(t);
        return convertEntityToDto(saved);
    }

    @Override
    @Transactional
    public TechnicianResponseDTO unassignFromMachine(Long technicianId) {
        Technician t = findTechnicianOrThrow(technicianId);
        t.setMachineAssignee(null);
        Technician saved = technicianRepository.save(t);
        return convertEntityToDto(saved);
    }

    private Technician findTechnicianOrThrow(Long id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technician not found with id=" + id));
    }

    private Machine findMachineOrThrow(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with id=" + id));
    }

    private void setSkillIfPresent(Technician t, Long skillId) {
        if (skillId == null) {
            t.setSkill(null);
            return;
        }
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id=" + skillId));
        t.setSkill(skill);
    }

    private void setMachineIfPresent(Technician t, Long machineId) {
        if (machineId == null) {
            t.setMachineAssignee(null);
            return;
        }
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with id=" + machineId));
        t.setMachineAssignee(machine);
    }
}
