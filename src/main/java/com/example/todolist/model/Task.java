package com.example.todolist.model;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task description can't be empty")
    private String description;
    private boolean done;

    public Task() {
        // TODO document why this constructor is empty
    }
}
