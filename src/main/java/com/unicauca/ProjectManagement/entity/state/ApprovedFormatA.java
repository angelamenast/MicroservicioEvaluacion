package com.unicauca.ProjectManagement.entity.state;

import com.unicauca.ProjectManagement.entity.DegreeProject;
import jakarta.persistence.Entity;

@Entity
public class ApprovedFormatA extends State {
    @Override
    public void changeState(DegreeProject degreeProject,String answer) {

    }
}
