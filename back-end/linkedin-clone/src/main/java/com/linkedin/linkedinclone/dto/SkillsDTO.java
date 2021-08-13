package com.linkedin.linkedinclone.dto;

import com.linkedin.linkedinclone.model.SkillsAndExperience;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SkillsDTO {
    private Set<SkillsAndExperience> education;
    private Set<SkillsAndExperience> workExperience;
    private Set<SkillsAndExperience> skills;
}
