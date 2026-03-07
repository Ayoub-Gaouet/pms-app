package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.SkillRequestDTO;
import com.ayoub.pmsapp.dto.SkillResponseDTO;
import com.ayoub.pmsapp.entities.Skill;

import java.util.List;

public interface SkillService {
    List<SkillResponseDTO> getAllSkills();
    SkillResponseDTO findSkillById(Long id);
    SkillResponseDTO saveSkill(SkillRequestDTO skillDTO);
    SkillResponseDTO updateSkill(Long id, SkillRequestDTO skillDTO);
    void deleteSkill(Long id);
    SkillResponseDTO convertEntityToDto(Skill skill);
    Skill convertDtoToEntity(SkillRequestDTO skillDTO);
}

