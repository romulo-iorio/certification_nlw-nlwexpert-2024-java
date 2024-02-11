package com.example.certification_nlw.modules.students.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAndAnswerDTO {
    private UUID questionID;
    private UUID alternativeID;
    private boolean isCorrect;
}
