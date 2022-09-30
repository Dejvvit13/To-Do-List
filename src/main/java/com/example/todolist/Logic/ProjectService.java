package com.example.todolist.Logic;

import com.example.todolist.Repository.ProjectRepository;
import com.example.todolist.Repository.TaskGroupRepository;
import com.example.todolist.TaskConfigurationProperties;
import com.example.todolist.model.Project;
import com.example.todolist.model.Projection.GroupReadModel;
import com.example.todolist.model.Projection.GroupTaskWriteModel;
import com.example.todolist.model.Projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskConfigurationProperties config;
    private TaskGroupService taskGroupService;

    public ProjectService(ProjectRepository projectRepository, TaskGroupRepository taskGroupRepository, TaskGroupService tGService, TaskConfigurationProperties config) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupService = tGService;
        this.config = config;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project createProject(Project source) {
        return projectRepository.save(source);
    }

    // createGroup on GroupWriteModel DTO
    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        return projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                        var model = new GroupTaskWriteModel();
                                        model.setDescription(projectStep.getDescription());
                                        model.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                        return model;
                                    }).collect(Collectors.toSet())
                    );
                    return taskGroupService.createGroup(targetGroup);
                })
                .orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }

    // createGroup on GroupModel
    //    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
//        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
//            throw new IllegalStateException("Only one undone group from project is allowed");
//        }
//        TaskGroup result = projectRepository.findById(projectId)
//                .map(project -> {
//                    var targetGroup = new TaskGroup();
//                    targetGroup.setDescription(project.getDescription());
//                    targetGroup.setTasks(
//                            project.getSteps().stream()
//                                    .map(projectStep -> new Task(
//                                            projectStep.getDescription(),
//                                            deadline.plusDays(projectStep.getDaysToDeadline()))
//                                    ).collect(Collectors.toSet())
//                    );
//                    return taskGroupRepository.save(targetGroup);
//                })
//                .orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
//        return new GroupReadModel(result);
//    }

}
