package com.example.certification_nlw.modules.students.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.certification_nlw.modules.questions.entities.QuestionEntity;
import com.example.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.example.certification_nlw.modules.students.dto.QuestionAndAnswerDTO;
import com.example.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.example.certification_nlw.modules.students.dto.StudentHasCertificationDTO;
import com.example.certification_nlw.modules.students.repostiories.CertificationRepository;
import com.example.certification_nlw.modules.students.repostiories.StudentRepository;
import com.example.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.example.certification_nlw.modules.students.entities.CertificationEntity;
import com.example.certification_nlw.modules.students.entities.StudentEntity;

@Service
public class StudentCertificationsAnswersUseCase {
        @Autowired
        private StudentRepository studentRepository;

        @Autowired
        private QuestionRepository questionRepository;

        @Autowired
        private CertificationRepository certificationRepository;

        @Autowired
        private StudentHasCertificationUseCase studentHasCertificationUseCase;

        public CertificationEntity execute(StudentCertificationAnswerDTO studentCertificationAnswerDTO)
                        throws Exception {
                verifyIfStudentHasCertification(studentCertificationAnswerDTO);
                var student = this.getOrCreateStudent(studentCertificationAnswerDTO);

                List<AnswersCertificationsEntity> answersCertifications = this
                                .getAnswersCertifications(studentCertificationAnswerDTO);

                int correctAnswers = answersCertifications.stream().filter(answer -> answer.isCorrect()).toList()
                                .size();

                CertificationEntity certification = CertificationEntity.builder()
                                .technology(studentCertificationAnswerDTO.getTechnology())
                                .grade(correctAnswers)
                                .studentID(student.getId()).build();

                var createdCertification = certificationRepository.save(certification);

                answersCertifications.forEach(answer -> {
                        answer.setCertificationID(certification.getId());
                        answer.setCertificationEntity(certification);
                });

                certification.setAnswers(answersCertifications);

                certificationRepository.save(certification);

                return createdCertification;
        }

        private void verifyIfStudentHasCertification(StudentCertificationAnswerDTO studentCertificationAnswerDTO)
                        throws Exception {
                StudentHasCertificationDTO studentHasCertificationDTO = StudentHasCertificationDTO.builder()
                                .email(studentCertificationAnswerDTO.getEmail())
                                .technology(studentCertificationAnswerDTO.getTechnology())
                                .build();

                boolean studentHasCertification = studentHasCertificationUseCase
                                .execute(studentHasCertificationDTO);

                if (studentHasCertification)
                        throw new Exception("Student already has certification for this technology");
        }

        private StudentEntity getOrCreateStudent(StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
                var student = studentRepository.findByEmail(studentCertificationAnswerDTO.getEmail());

                if (!student.isEmpty())
                        return student.get();

                StudentEntity studentToCreate = StudentEntity
                                .builder()
                                .email(studentCertificationAnswerDTO.getEmail())
                                .build();
                studentRepository.save(studentToCreate);

                return studentToCreate;
        }

        private AnswersCertificationsEntity mapCorrectAlternatives(QuestionAndAnswerDTO questionAndAnswer,
                        List<QuestionEntity> questions) {
                var question = questions.stream()
                                .filter(thisQuestion -> thisQuestion.getId().equals(questionAndAnswer.getQuestionID()))
                                .findFirst()
                                .get();

                var correctAlternative = question.getAlternatives().stream()
                                .filter(alternative -> alternative.isCorrect())
                                .findFirst()
                                .get();

                boolean isCorrectAlternative = correctAlternative.getId().equals(questionAndAnswer.getAlternativeID());
                questionAndAnswer.setCorrect(isCorrectAlternative);

                return AnswersCertificationsEntity.builder()
                                .questionID(questionAndAnswer.getQuestionID())
                                .answerID(questionAndAnswer.getAlternativeID())
                                .isCorrect(isCorrectAlternative)
                                .build();
        }

        private List<AnswersCertificationsEntity> getAnswersCertifications(
                        StudentCertificationAnswerDTO studentCertificationAnswerDTO) throws Exception {
                List<QuestionEntity> questions = questionRepository
                                .findByTechnology(studentCertificationAnswerDTO.getTechnology());

                if (questions.isEmpty())
                        throw new Exception("No questions found for this technology");

                var questionsAndAnswers = studentCertificationAnswerDTO.getQuestionsAndAnswers();

                return questionsAndAnswers.stream()
                                .map(questionAndAnswer -> this.mapCorrectAlternatives(questionAndAnswer, questions))
                                .toList();
        }
}
