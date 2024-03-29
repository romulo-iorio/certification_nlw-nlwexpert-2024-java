package com.example.certification_nlw.modules.students.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentHasCertificationDTO {
    private String email;
    private String technology;
}
