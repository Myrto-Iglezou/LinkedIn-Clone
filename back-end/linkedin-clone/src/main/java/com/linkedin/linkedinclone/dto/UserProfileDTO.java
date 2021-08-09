package com.linkedin.linkedinclone.dto;

import com.linkedin.linkedinclone.model.SkillsAndExperience;
import com.linkedin.linkedinclone.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String name;
    private String surname;
    private String position;
    private String company;
    private Boolean isConnected;
    private Set<User> network;
    private Set<SkillsAndExperience> skills;

}
