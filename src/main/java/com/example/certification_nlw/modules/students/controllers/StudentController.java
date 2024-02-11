package com.example.certification_nlw.modules.students.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.example.certification_nlw.modules.students.dto.StudentHasCertificationDTO;
import com.example.certification_nlw.modules.students.entities.CertificationEntity;
import com.example.certification_nlw.modules.students.useCases.StudentCertificationsAnswersUseCase;
import com.example.certification_nlw.modules.students.useCases.StudentHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentHasCertificationUseCase studentHasCertificationUseCase;

    @Autowired
    private StudentCertificationsAnswersUseCase studentCertificationsAnswersUseCase;

    @RequestMapping("/has-certification")
    public String hasCertification(@RequestBody StudentHasCertificationDTO studentHasCertificationDTO) {
        var hasCertification = studentHasCertificationUseCase.execute(studentHasCertificationDTO);

        if (hasCertification)
            return "Já fez a prova";

        System.out.println(studentHasCertificationDTO);
        return "Pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswer(
            @RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
        try {
            var result = this.studentCertificationsAnswersUseCase.execute(studentCertificationAnswerDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
