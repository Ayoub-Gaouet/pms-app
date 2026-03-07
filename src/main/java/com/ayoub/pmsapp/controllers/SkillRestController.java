package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.dto.SkillRequestDTO;
import com.ayoub.pmsapp.dto.SkillResponseDTO;
import com.ayoub.pmsapp.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin("*")
public class SkillRestController {
    private final SkillService skillService;

    public SkillRestController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<SkillResponseDTO> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public SkillResponseDTO findSkillById(@PathVariable("id") Long id) {
        return skillService.findSkillById(id);
    }

    @PostMapping
    public SkillResponseDTO saveSkill(@Valid @RequestBody SkillRequestDTO skillDTO) {
        return skillService.saveSkill(skillDTO);
    }

    @PutMapping("/{id}")
    public SkillResponseDTO updateSkill(@PathVariable Long id, @Valid @RequestBody SkillRequestDTO skillDTO) {
        return skillService.updateSkill(id, skillDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }
}

