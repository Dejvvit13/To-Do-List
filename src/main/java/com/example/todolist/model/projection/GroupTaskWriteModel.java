package com.example.todolist.model.projection;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupTaskWriteModel {

    private String description;
    private LocalDateTime deadline;

    Task toTask(final TaskGroup group) {
        return new Task(description, deadline, group);
    }

}
