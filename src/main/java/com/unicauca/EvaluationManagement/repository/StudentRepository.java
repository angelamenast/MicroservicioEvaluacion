package com.unicauca.EvaluationManagement.repository;

import com.unicauca.EvaluationManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {

}
