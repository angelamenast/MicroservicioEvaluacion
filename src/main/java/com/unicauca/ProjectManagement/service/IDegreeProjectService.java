package com.unicauca.ProjectManagement.service;

import com.unicauca.ProjectManagement.entity.DegreeProject;
import com.unicauca.ProjectManagement.infra.dto.DegreeProjectRequest;
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
