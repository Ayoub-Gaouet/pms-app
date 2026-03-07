package com.ayoub.pmsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

