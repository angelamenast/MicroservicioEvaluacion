package com.unicauca.ProjectManagement.service;

import com.unicauca.ProjectManagement.entity.*;
import com.unicauca.ProjectManagement.entity.state.FirstReviewFormatA;
import com.unicauca.ProjectManagement.entity.state.State;
import com.unicauca.ProjectManagement.infra.dto.DegreeProjectRequest;
import com.unicauca.ProjectManagement.infra.dto.FileRequest;
import com.unicauca.ProjectManagement.repository.DegreeProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class DegreeProjectService implements IDegreeProjectService {
    @Autowired
    private final DegreeProjectRepository degreeProjectRepository;

    @Autowired
    private final ProfessorService professorService;

    @Autowired
    private final StudentService studentService;

    public DegreeProjectService(DegreeProjectRepository degreeProjectRepository, ProfessorService professorService, StudentService studentService) {
        this.degreeProjectRepository = degreeProjectRepository;
        this.professorService = professorService;
        this.studentService = studentService;
    }

    @Override
    @Transactional
    public DegreeProject saveProject(DegreeProjectRequest degreeProjectRequest) throws Exception {
        DegreeProject degreeProject = new DegreeProject();
        degreeProject.setTitle(degreeProjectRequest.getTitle());
        degreeProject.setGeneralObjective(degreeProjectRequest.getGeneralObjective());
        degreeProject.setSpecificObjectives(degreeProjectRequest.getSpecificObjectives());
        degreeProject.setModality(String.valueOf(degreeProjectRequest.getModality()));

        degreeProject.setDirector(professorService.findById(degreeProjectRequest.getDirectorId()).get());
        String stateName = degreeProjectRequest.getState().getName();
        State state = null;
        switch (stateName) {
            case "FirstReviewFormatA" -> state = new FirstReviewFormatA();
            //Agregar los demas cases para los otros estados
        }

        degreeProject.setState(state);

        List<File> files = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        for (Long studentId : degreeProjectRequest.getStudentsId()) {
            students.add(studentService.findById(studentId).get());
        }

        for (FileRequest fileRequest : degreeProjectRequest.getFiles()) {
            File file = new File();
            file.setName(fileRequest.getName());
            file.setType(String.valueOf(fileRequest.getType()));
            file.setVersion(fileRequest.getVersion());
            file.setDocument(Base64.getDecoder().decode(fileRequest.getDocument()));
            files.add(file);
        }

        List<Professor> codirectors = new ArrayList<>();

        for (Long codirectorId : degreeProjectRequest.getCodirectorsId()) {
            codirectors.add(professorService.findById(codirectorId).get());
        }

        degreeProject.setCodirectors(codirectors);
        degreeProject.setFiles(files);
        degreeProject.setStudents(students);

        return degreeProjectRepository.save(degreeProject);

    }

    @Transactional(readOnly = true)
    public DegreeProject getActiveProjectByStudentEmail(String email) throws Exception {
        try {
            List<DegreeProject> projects = degreeProjectRepository.findProjectsByStudentEmail(email);

            if (projects == null || projects.isEmpty()) {
                return null;
            }
            // Buscar el proyecto que no esté en estado "Rejected"
            return projects.stream()
                    .filter(p -> p.getState() != null
                            && p.getState().getName() != null
                            && !p.getState().getName().equalsIgnoreCase("RejectedProject"))
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            throw new Exception("Error al obtener el proyecto activo del estudiante: " + e.getMessage(), e);
        }
    }


    @Override
    @Transactional
    public DegreeProject saveProject(DegreeProject degreeProject) throws Exception {
        return degreeProjectRepository.save(degreeProject);
    }

    @Override
    @Transactional
    public List<DegreeProject> findAllProjects() throws Exception {
        try {
            return degreeProjectRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error listing projects: " + e.getMessage());
        }
    }

}
