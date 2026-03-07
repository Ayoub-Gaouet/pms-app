package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.SkillRequestDTO;
import com.ayoub.pmsapp.dto.SkillResponseDTO;
import com.ayoub.pmsapp.entities.Skill;
import com.ayoub.pmsapp.repository.SkillRepository;
import com.ayoub.pmsapp.service.SkillService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;

    public SkillServiceImpl(SkillRepository skillRepository, ModelMapper modelMapper) {
        this.skillRepository = skillRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SkillResponseDTO> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    public SkillResponseDTO findSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        return convertEntityToDto(skill);
    }

    @Override
    public SkillResponseDTO saveSkill(SkillRequestDTO skillDTO) {
        Skill skill = convertDtoToEntity(skillDTO);
        Skill saved = skillRepository.save(skill);
        return convertEntityToDto(saved);
    }

    @Override
    public SkillResponseDTO updateSkill(Long id, SkillRequestDTO skillDTO) {
        Skill skill = convertDtoToEntity(skillDTO);
        skill.setId(id);
        Skill updated = skillRepository.save(skill);
        return convertEntityToDto(updated);
    }

    @Override
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

    @Override
    public SkillResponseDTO convertEntityToDto(Skill skill) {
        SkillResponseDTO responseDTO = new SkillResponseDTO();
        responseDTO.setId(skill.getId());
        responseDTO.setName(skill.getName());
        responseDTO.setCreated_at(skill.getCreated_at());
        responseDTO.setUpdated_at(skill.getUpdated_at());
        return responseDTO;
    }

    @Override
    public Skill convertDtoToEntity(SkillRequestDTO skillDTO) {
        Skill skill = new Skill();
        skill.setName(skillDTO.getName());
        return skill;
    }
}

