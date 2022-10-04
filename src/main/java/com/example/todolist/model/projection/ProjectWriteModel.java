package com.example.todolist.model.projection;

import com.example.todolist.model.Project;
import com.example.todolist.model.ProjectStep;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public class ProjectWriteModel {
    @NotBlank(message = "Project's description must not be empty")
    private String description;
    @Valid
    private List<ProjectStep> steps = new ArrayList<>();

    public Project toProject() {
        var result = new Project();
        result.setDescription(this.description);
        this.steps.forEach(step -> step.setProject(result));
        result.setSteps(new HashSet<>(this.steps));
        return result;
    }

}
