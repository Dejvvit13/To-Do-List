package com.example.todolist;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {

    private Template template = new Template();

    @Getter
    @Setter
    public static class Template {
        boolean isAllowMultipleTasks;

        public boolean isAllowMultipleTasks() {
            return isAllowMultipleTasks;
        }
        public void setAllowMultipleTasks(boolean allowMultipleTasks) {
            isAllowMultipleTasks = allowMultipleTasks;
        }
    }


}
