package com.example.todolist.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter(AccessLevel.PACKAGE)
@MappedSuperclass
public abstract class BaseTaskClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Description can't be empty") String description;

    @Setter(AccessLevel.PUBLIC)
    boolean done;

    @Embedded
    @Getter(AccessLevel.PRIVATE)
    private Audit audit = new Audit();

}
