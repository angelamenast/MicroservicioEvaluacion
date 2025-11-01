package com.unicauca.EvaluationManagement.entity.state;

import com.unicauca.EvaluationManagement.entity.DegreeProject;

public class SecondCorrectionFormatA extends State{

    @Override
    public void changeState(DegreeProject degreeProject, String answer) {
        degreeProject.setState(new ThirdReviewFormatA());
    }
}
