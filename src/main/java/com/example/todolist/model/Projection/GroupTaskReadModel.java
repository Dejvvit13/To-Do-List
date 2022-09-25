package com.example.todolist.model.Projection;

import com.example.todolist.model.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GroupTaskReadModel {

    private String description;
    private Boolean done;

    public GroupTaskReadModel(Task source) {
        this.description = source.getDescription();
        this.done = source.isDone();
    }

}
