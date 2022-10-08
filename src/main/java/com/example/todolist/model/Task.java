package com.example.todolist.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task group's description must not be empty")
    private String description;

    @Setter(AccessLevel.PUBLIC)
    private boolean done;
    
    private LocalDateTime deadline;
    @Embedded
    @Getter(AccessLevel.PACKAGE)
    private Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    @Getter(AccessLevel.PACKAGE)
    private TaskGroup group;


    public Task(String description, LocalDateTime deadline) {
        this(description, deadline, null);
    }

    public Task(String description, LocalDateTime deadline, TaskGroup group) {
        this.deadline = deadline;
        this.description = description;
        if (group != null) {
            this.group = group;
        }
    }

    public void updateFrom(final Task source) {
        this.description = source.description;
        this.done = source.done;
        this.deadline = source.deadline;
        this.group = source.group;
    }


}
