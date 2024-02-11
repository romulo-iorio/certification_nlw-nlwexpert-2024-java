package com.example.certification_nlw.modules.certifications.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.example.certification_nlw.modules.certifications.useCases.GetTop10StudentsRankingUseCase;
import com.example.certification_nlw.modules.students.entities.CertificationEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private GetTop10StudentsRankingUseCase getTop10StudentsRankingUseCase;

    @RequestMapping(value = "/top10")
    public List<CertificationEntity> getTop10Students() {
        return getTop10StudentsRankingUseCase.execute();
    }
}
