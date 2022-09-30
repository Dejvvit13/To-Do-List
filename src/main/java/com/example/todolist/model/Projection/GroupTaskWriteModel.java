package com.example.todolist.model.Projection;

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

    public Task toTask(TaskGroup result) {
        return new Task(description, deadline);
    }

}
