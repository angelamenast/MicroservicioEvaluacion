package com.unicauca.ProjectManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
public class File {

    @Id
    @Getter
    @Setter
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String type;
    @Getter @Setter
    private int version;
    @Getter@Setter
    @Lob
    private byte[] document;

}
