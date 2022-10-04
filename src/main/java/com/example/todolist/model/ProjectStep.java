package com.example.todolist.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Table(name = "project_steps")
public class ProjectStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private int id;
    @NotBlank(message = "Project's step description must not be empty")
    private String description;
    private int daysToDeadline;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @Getter(AccessLevel.PACKAGE)
    private Project project;

}
