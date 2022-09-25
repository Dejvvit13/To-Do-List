package com.example.todolist.Logic;

import com.example.todolist.Repository.ProjectRepository;
import com.example.todolist.Repository.TaskGroupRepository;
import com.example.todolist.TaskConfigurationProperties;
import com.example.todolist.model.Project;
import com.example.todolist.model.Projection.GroupReadModel;
import com.example.todolist.model.Projection.GroupTaskWriteModel;
import com.example.todolist.model.Projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskConfigurationProperties config;

    public ProjectService(ProjectRepository projectRepository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project createProject(Project source) {
        return projectRepository.save(source);
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if (taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId) && !config.getTemplate().isAllowMultipleTasks()) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        GroupWriteModel groupWriteModel = projectRepository.findById(projectId)
                .map(project -> {
                    var result = new GroupWriteModel();
                    result.setDescription(project.getDescription());
                    result.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                        var model = new GroupTaskWriteModel();
                                        model.setDescription(projectStep.getDescription());
                                        model.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                        return model;
                                    }).collect(Collectors.toSet())
                    );
                    return result;
                })
                .orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(groupWriteModel.toGroup());
    }

}
