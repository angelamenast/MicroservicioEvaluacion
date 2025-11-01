package com.unicauca.EvaluationManagement.service;

import com.unicauca.EvaluationManagement.entity.DegreeProject;
import com.unicauca.EvaluationManagement.infra.dto.DegreeProjectRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IDegreeProjectService {

    @Transactional
    public DegreeProject saveProject(DegreeProjectRequest degreeProjectRequest)throws Exception;

    @Transactional
    DegreeProject saveProject(DegreeProject degreeProject)throws Exception;

    @Transactional
    List<DegreeProject> findAllProjects() throws Exception;

}
