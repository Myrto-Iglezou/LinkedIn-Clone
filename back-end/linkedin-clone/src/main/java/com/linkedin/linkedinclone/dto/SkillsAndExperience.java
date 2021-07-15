package com.linkedin.linkedinclone.dto;

import com.linkedin.linkedinclone.enumerations.PublicOrPrivate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillsAndExperience {
    private Map<String, PublicOrPrivate> Experience;
    private Map<String, PublicOrPrivate> Education;
    private Map<String, PublicOrPrivate> Skills;
}
