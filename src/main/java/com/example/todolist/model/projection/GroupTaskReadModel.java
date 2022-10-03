package com.example.todolist.model.projection;

import com.example.todolist.model.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupTaskReadModel {

    private String description;
    private Boolean done;

    GroupTaskReadModel(Task source) {
        this.description = source.getDescription();
        this.done = source.isDone();
    }

}
