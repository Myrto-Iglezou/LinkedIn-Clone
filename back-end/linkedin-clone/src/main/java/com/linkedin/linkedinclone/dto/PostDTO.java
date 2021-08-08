package com.linkedin.linkedinclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class PostDTO {
    private String content;
    private Timestamp timestamp;
    private Long userId;
}
