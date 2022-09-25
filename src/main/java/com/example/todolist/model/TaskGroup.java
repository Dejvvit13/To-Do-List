package com.example.todolist.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@Entity
@Table(name = "task_groups")
public class TaskGroup extends BaseTaskClass {


    @Embedded
    @Getter(AccessLevel.PRIVATE)
    private Audit audit = new Audit();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public void updateFrom(final TaskGroup source) {
        this.description = source.description;
        this.done = source.done;
    }


}
