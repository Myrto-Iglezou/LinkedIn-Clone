package com.linkedin.linkedinclone.dto;

import lombok.Data;

@Data
public class NewUserInfo {
    private String currentPassword;
    private String newPassword=null;
    private String passwordConfirm=null;
    private String currentUsername;
    private String newUsername=null;
}
