package com.example.certification_nlw.modules.students.repostiories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.certification_nlw.modules.students.entities.CertificationEntity;

@Repository
public interface CertificationRepository extends JpaRepository<CertificationEntity, UUID> {

    @Query("SELECT c FROM certifications c INNER JOIN c.studentEntity std WHERE std.email = :email AND c.technology = :technology")
    List<CertificationEntity> findByStudentEmailAndTechnology(String email, String technology);
}
