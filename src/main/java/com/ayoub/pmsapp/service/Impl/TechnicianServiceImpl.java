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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TechnicianServiceImpl implements TechnicianService {

    private final TechnicianRepository technicianRepository;
    private final SkillRepository skillRepository;
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    public TechnicianServiceImpl(TechnicianRepository technicianRepository,
                                 SkillRepository skillRepository,
                                 MachineRepository machineRepository,
                                 ModelMapper modelMapper) {
        this.technicianRepository = technicianRepository;
        this.skillRepository = skillRepository;
        this.machineRepository = machineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public TechnicianResponseDTO convertEntityToDto(Technician technician) {
        TechnicianResponseDTO dto = modelMapper.map(technician, TechnicianResponseDTO.class);
        if (technician.getSkill() != null) {
            dto.setSkillId(technician.getSkill().getId());
            dto.setSkillName(technician.getSkill().getName());
        }
        if (technician.getMachineAssignee() != null) {
            dto.setMachineAssigneeId(technician.getMachineAssignee().getId());
            dto.setMachineAssigneeNom(technician.getMachineAssignee().getNom());
        }
        return dto;
    }

    @Override
    public Technician convertDtoToEntity(TechnicianRequestDTO dto) {
        Technician t = modelMapper.map(dto, Technician.class);
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
        return convertEntityToDto(findTechnicianById(id));
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
        Technician existing = findTechnicianById(id);
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
        Technician t = findTechnicianById(id);
        technicianRepository.delete(t);
    }

    @Override
    @Transactional
    public TechnicianResponseDTO assignToMachine(Long technicianId, Long machineId) {
        Technician t = findTechnicianById(technicianId);
        Machine m = findMachineOrThrow(machineId);
        t.setMachineAssignee(m);
        Technician saved = technicianRepository.save(t);
        return convertEntityToDto(saved);
    }

    @Override
    @Transactional
    public TechnicianResponseDTO unassignFromMachine(Long technicianId) {
        Technician t = findTechnicianById(technicianId);
        t.setMachineAssignee(null);
        Technician saved = technicianRepository.save(t);
        return convertEntityToDto(saved);
    }

    @Override
    public Technician findTechnicianById(Long id) {
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
