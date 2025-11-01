package com.unicauca.EvaluationManagement.entity.state;

import com.unicauca.EvaluationManagement.entity.DegreeProject;
import jakarta.persistence.Entity;

@Entity
public class ApprovedFormatA extends State {
    @Override
    public void changeState(DegreeProject degreeProject,String answer) {

    }
}
