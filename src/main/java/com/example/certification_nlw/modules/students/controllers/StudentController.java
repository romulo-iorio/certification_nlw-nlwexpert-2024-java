package com.example.certification_nlw.modules.students.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.certification_nlw.modules.students.dto.StudentHasCertificationDTO;
import com.example.certification_nlw.modules.students.useCases.StudentHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentHasCertificationUseCase studentHasCertificationUseCase;

    @RequestMapping("/has-certification")
    public String hasCertification(@RequestBody StudentHasCertificationDTO studentHasCertificationDTO) {
        var hasCertification = studentHasCertificationUseCase.execute(studentHasCertificationDTO);

        if (hasCertification)
            return "Já fez a prova";

        System.out.println(studentHasCertificationDTO);
        return "Pode fazer a prova";
    }
}
