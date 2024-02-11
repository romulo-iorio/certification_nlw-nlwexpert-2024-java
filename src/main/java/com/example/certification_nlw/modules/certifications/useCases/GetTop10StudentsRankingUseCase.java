package com.example.certification_nlw.modules.certifications.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.certification_nlw.modules.students.entities.CertificationEntity;
import com.example.certification_nlw.modules.students.repostiories.CertificationRepository;

@Service
public class GetTop10StudentsRankingUseCase {
    @Autowired
    private CertificationRepository certificationRepository;

    public List<CertificationEntity> execute() {
        return certificationRepository.findTop10StudentsOrderByGradeDesc();
    }
}
