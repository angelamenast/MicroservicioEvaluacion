package com.unicauca.EvaluationManagement.repository;

import com.unicauca.EvaluationManagement.entity.DegreeProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DegreeProjectRepository extends JpaRepository<DegreeProject,Long>{

    @Query("SELECT dp FROM DegreeProject dp JOIN dp.students s WHERE s.email = :email")
    List<DegreeProject> findProjectsByStudentEmail(@Param("email") String email);
}
