package com.example.todolist.logic;

import com.example.todolist.repository.TaskRepository;
import com.example.todolist.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Async
    public CompletableFuture<List<Task>> findAllAsync() {
        logger.info("Supply async");
        return CompletableFuture.supplyAsync(taskRepository::findAll);
    }
}
