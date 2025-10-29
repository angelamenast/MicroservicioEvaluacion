package com.unicauca.ProjectManagement.service;

import com.unicauca.ProjectManagement.entity.*;
import com.unicauca.ProjectManagement.entity.state.FirstReviewFormatA;
import com.unicauca.ProjectManagement.entity.state.State;
import com.unicauca.ProjectManagement.infra.config.RabbitMQConfig;
import com.unicauca.ProjectManagement.infra.dto.DegreeProjectEvent;
import com.unicauca.ProjectManagement.infra.dto.FileEvent;
import com.unicauca.ProjectManagement.repository.DegreeProjectRepository;
import com.unicauca.ProjectManagement.repository.ProfessorRepository;
import com.unicauca.ProjectManagement.repository.StudentRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class DegreeProjectConsumerService {

    @Autowired
    private final DegreeProjectRepository degreeProjectRepository;

    @Autowired
    private final ProfessorRepository professorRepository;

    @Autowired
    private final StudentRepository studentRepository;

    public DegreeProjectConsumerService(DegreeProjectRepository degreeProjectRepository, ProfessorRepository professorRepository, StudentRepository studentRepository) {
        this.degreeProjectRepository = degreeProjectRepository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.PROJECT_QUEUE)
    public void receiveDegreeProject(DegreeProjectEvent degreeProject) throws Exception {
        try {

            DegreeProject degreeProjectSaved = new DegreeProject();

            degreeProjectSaved.setId(degreeProject.getId());
            degreeProjectSaved.setTitle(degreeProject.getTitle());
            degreeProjectSaved.setGeneralObjective(degreeProject.getGeneralObjective());
            degreeProjectSaved.setSpecificObjectives(degreeProject.getSpecificObjectives());
            degreeProjectSaved.setModality(degreeProject.getModality());

            List<File> files = new ArrayList<>();
            for (FileEvent fileEvent : degreeProject.getFiles()) {
                File file = new File();
                file.setId(fileEvent.getId());
                file.setName(fileEvent.getName());
                file.setType(String.valueOf(fileEvent.getType()));
                file.setVersion(fileEvent.getVersion());
                file.setDocument(Base64.getDecoder().decode(fileEvent.getDocument()));
                files.add(file);
            }

            degreeProjectSaved.setFiles(files);

            List<Student> students = new ArrayList<>();
            for (Long studentId : degreeProject.getStudentsId()) {
                students.add(studentRepository.findById(studentId).get());
            }

            degreeProjectSaved.setStudents(students);

            Optional<Professor> director = professorRepository.findById(degreeProject.getDirectorId());

            degreeProjectSaved.setDirector(director.get());

            List<Professor> codirectors = new ArrayList<>();
            for (Long codirectorId : degreeProject.getCodirectorsId()) {
                codirectors.add(professorRepository.findById(codirectorId).get());
            }

            degreeProjectSaved.setCodirectors(codirectors);

            String stateName = degreeProject.getState().getName();
            State state = null;
            switch (stateName) {
                case "FirstReviewFormatA" -> state = new FirstReviewFormatA();
            }

            state.setId(degreeProjectSaved.getId());

            degreeProjectSaved.setState(state);



            degreeProjectRepository.save(degreeProjectSaved);

            System.out.println("Student received");

        } catch(Exception e){
            throw new Exception("Error receiving a professor: " + e.getMessage());
        }
    }

}


