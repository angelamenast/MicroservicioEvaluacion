package com.unicauca.EvaluationManagement.service;

import com.unicauca.EvaluationManagement.entity.Professor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IProfessorService {

    @Transactional
    Optional<Professor> findById(Long id) throws Exception;

    @Transactional
    List<Professor> findAllProfessors() throws Exception;

}
