package com.example.todolist.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends BaseTaskClass {

    private LocalDateTime deadline;

    @Getter(AccessLevel.PACKAGE)
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;


    public void updateFrom(final Task source) {
        this.description = source.description;
        this.done = source.done;
        this.deadline = source.deadline;
        this.group = source.group;
    }


}
