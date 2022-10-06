package com.example.todolist.model.projection;

import com.example.todolist.model.Project;
import com.example.todolist.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroupWriteModel {

    private String id;
    @NotBlank(message = "Task group's description must not be empty")
    private String description;
    @Valid
    private List<GroupTaskWriteModel> tasks = new ArrayList<>();
    private Project project;

    public GroupWriteModel() {
        tasks.add(new GroupTaskWriteModel());
    }

    public TaskGroup toGroup(final Project project) {
        var result = new TaskGroup();
        result.setProject(this.project);
        result.setDescription(description);
        result.setTasks(
                tasks.stream()
                        .map(source -> source.toTask(result))
                        .toList()
        );
        result.setProject(project);
        return result;
    }

}
