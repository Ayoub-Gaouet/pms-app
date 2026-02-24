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
        TechnicianResponseDTO technicianResponseDTO = modelMapper.map(technician, TechnicianResponseDTO.class);
        if (technician.getSkill() != null) {
            technicianResponseDTO.setSkillId(technician.getSkill().getId());
            technicianResponseDTO.setSkillName(technician.getSkill().getName());
        }
        if (technician.getMachineAssignee() != null) {
            technicianResponseDTO.setMachineAssigneeId(technician.getMachineAssignee().getId());
            technicianResponseDTO.setMachineAssigneeNom(technician.getMachineAssignee().getNom());
        }
        return technicianResponseDTO;
    }

    @Override
    public Technician convertDtoToEntity(TechnicianRequestDTO dto) {
        Technician technician = modelMapper.map(dto, Technician.class);
        setSkillIfPresent(technician, dto.getSkillId());
        setMachineIfPresent(technician, dto.getMachineAssigneeId());
        return technician;
    }

    @Override
    @Transactional
    public TechnicianResponseDTO createTechnician(TechnicianRequestDTO technicianRequestDTO) {
        Technician technician = convertDtoToEntity(technicianRequestDTO);
        Technician saved = technicianRepository.save(technician);
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
        Technician technician = findTechnicianById(technicianId);
        Machine machine = findMachineOrThrow(machineId);
        technician.setMachineAssignee(machine);
        Technician saved = technicianRepository.save(technician);
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

    private void setSkillIfPresent(Technician technician, Long skillId) {
        if (skillId == null) {
            technician.setSkill(null);
            return;
        }
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id=" + skillId));
        technician.setSkill(skill);
    }

    private void setMachineIfPresent(Technician technician, Long machineId) {
        if (machineId == null) {
            technician.setMachineAssignee(null);
            return;
        }
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with id=" + machineId));
        technician.setMachineAssignee(machine);
    }
}
