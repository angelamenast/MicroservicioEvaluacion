package com.unicauca.EvaluationManagement.entity.state;

import com.unicauca.EvaluationManagement.entity.DegreeProject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class State {
    @Id
    @Getter @Setter
    Long id;

    @Getter @Setter
    String name;

    public abstract void changeState(DegreeProject degreeProject, String answer);
}

