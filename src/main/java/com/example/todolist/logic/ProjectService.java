package com.example.todolist.logic;

import com.example.todolist.TaskConfigurationProperties;
import com.example.todolist.model.Project;
import com.example.todolist.model.projection.GroupReadModel;
import com.example.todolist.model.projection.GroupTaskWriteModel;
import com.example.todolist.model.projection.GroupWriteModel;
import com.example.todolist.model.projection.ProjectWriteModel;
import com.example.todolist.repository.ProjectRepository;
import com.example.todolist.repository.TaskGroupRepository;

import java.time.LocalDateTime;
import java.util.List;

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

    public Project save(final ProjectWriteModel toSave) {
        return projectRepository.save(toSave.toProject());
    }

    // createGroup on GroupWriteModel DTO
    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProjectId(projectId)) {
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
                                    }).toList()
                    );
                    return taskGroupService.createGroup(targetGroup, project);
                })
                .orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }

//      createGroup on GroupModel
//        public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
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
