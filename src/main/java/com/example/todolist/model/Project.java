package com.example.todolist.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.PACKAGE)
    private int id;
    @NotBlank(message = "Project's description must not be empty")
    private String description;

    @OneToMany(mappedBy = "project")
    @Setter(AccessLevel.PACKAGE)
    private Set<TaskGroup> groups;
    @Getter(AccessLevel.PUBLIC)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectStep> steps;

    public Project(String description, Set<TaskGroup> groups, Set<ProjectStep> steps) {
        this.description = description;
        this.groups = groups;
        this.steps = steps;
    }
}
