package com.example.certification_nlw.modules.questions.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.certification_nlw.modules.questions.dto.AlternativeResultDTO;
import com.example.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.example.certification_nlw.modules.questions.entities.AlternativeEntity;
import com.example.certification_nlw.modules.questions.entities.QuestionEntity;
import com.example.certification_nlw.modules.questions.repositories.QuestionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var questions = this.questionRepository.findByTechnology(technology);

        var questionsResultDTO = questions.stream().map(question -> mapQuestionToDTO(question))
                .collect(Collectors.toList());

        return questionsResultDTO;
    }

    static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
        var questionResultDTO = QuestionResultDTO.builder()
                .id(question.getId())
                .technology(question.getTechnology())
                .description(question.getDescription())
                .build();

        List<AlternativeResultDTO> alternativesResultDTO = question.getAlternatives().stream()
                .map(alternative -> mapAlternativeToDTO(alternative)).collect(Collectors.toList());

        questionResultDTO.setAlternatives(alternativesResultDTO);

        return questionResultDTO;
    }

    static AlternativeResultDTO mapAlternativeToDTO(AlternativeEntity alternative) {
        return AlternativeResultDTO.builder()
                .id(alternative.getId()).description(alternative.getDescription())
                .description(alternative.getDescription()).build();
    }
}
