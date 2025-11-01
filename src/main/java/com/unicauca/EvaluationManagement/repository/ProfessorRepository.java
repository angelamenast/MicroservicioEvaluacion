package com.unicauca.EvaluationManagement.repository;

import com.unicauca.EvaluationManagement.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor,Long> {
    

}
