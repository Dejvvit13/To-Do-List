package com.example.todolist.model.projection;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GroupTaskWriteModel {

    @NotBlank(message = "Task description must not be empty")

    private String description;
    private LocalDateTime deadline;


    Task toTask(final TaskGroup group) {
        return new Task(description, deadline, group);
    }

}
