package com.linkedin.linkedinclone.dto;


import com.linkedin.linkedinclone.model.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class NetworkUserDTO {
    private Long id;
    private String name;
    private String surname;
    private String position;
    private String company;

    // IMAGE MISSING
    // private Picture picture;
}
