package com.example.todolist.Logic;

import com.example.todolist.Repository.ProjectRepository;
import com.example.todolist.Repository.TaskGroupRepository;
import com.example.todolist.TaskConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {

    @Bean
    ProjectService service(
            final ProjectRepository projectRepository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties config
    ) {
        return new ProjectService(projectRepository, taskGroupRepository, config);
    }

}
