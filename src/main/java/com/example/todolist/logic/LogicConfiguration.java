package com.example.todolist.logic;

import com.example.todolist.repository.ProjectRepository;
import com.example.todolist.repository.TaskGroupRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.TaskConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {

    @Bean
    ProjectService projectService(
            final ProjectRepository projectRepository,
            final TaskGroupRepository taskGroupRepository,
            final TaskGroupService taskGroupService,
            final TaskConfigurationProperties config
    ) {
        return new ProjectService(projectRepository, taskGroupRepository, taskGroupService, config);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository taskGroupRepository,
            final TaskRepository taskRepository
    ) {
        return new TaskGroupService(taskGroupRepository, taskRepository);
    }

}
