package com.unicauca.ProjectManagement.entity.state;

import com.unicauca.ProjectManagement.entity.DegreeProject;

public class SecondCorrectionFormatA extends State{

    @Override
    public void changeState(DegreeProject degreeProject, String answer) {
        degreeProject.setState(new ThirdReviewFormatA());
    }
}
