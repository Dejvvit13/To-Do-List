package com.example.todolist.model.projection;

import com.example.todolist.model.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupTaskReadModel {

    private String description;
    private Boolean done;
    private LocalDateTime deadline;

    GroupTaskReadModel(Task source) {
        this.description = source.getDescription();
        this.done = source.isDone();
        this.deadline = source.getDeadline();
    }

}
