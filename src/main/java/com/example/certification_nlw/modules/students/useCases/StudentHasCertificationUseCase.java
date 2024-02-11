package com.example.certification_nlw.modules.students.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.certification_nlw.modules.students.dto.StudentHasCertificationDTO;
import com.example.certification_nlw.modules.students.repostiories.CertificationRepository;

@Service
public class StudentHasCertificationUseCase {
    @Autowired
    private CertificationRepository certificationRepository;

    public boolean execute(StudentHasCertificationDTO dto) {
        var certification = this.certificationRepository.findByStudentEmailAndTechnology(dto.getEmail(),
                dto.getTechnology());

        if (certification.isEmpty())
            return false;

        return true;
    }
}
