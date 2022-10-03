package com.example.todolist.model.projection;

import com.example.todolist.model.Project;
import com.example.todolist.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class GroupWriteModel {

    private String id;
    private String description;
    private Set<GroupTaskWriteModel> tasks;
    private Project project;

    public TaskGroup toGroup() {
        var result = new TaskGroup();
        result.setProject(project);
        result.setDescription(description);
        result.setTasks(
                tasks.stream()
                        .map(source -> source.toTask(result))
                        .collect(Collectors.toSet())
        );
        return result;
    }

}
