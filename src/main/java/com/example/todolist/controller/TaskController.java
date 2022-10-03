package com.example.todolist.controller;

import com.example.todolist.logic.TaskService;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public TaskController(final TaskRepository repository, TaskService taskService) {
        this.taskRepository = repository;
        this.taskService = taskService;
    }

    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {
        Task result = taskRepository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping(params = {"!page", "!sort", "!size"})
    CompletableFuture<ResponseEntity<List<Task>>> readAllTasks() {
        logger.warn("Exposing all tasks");
        return taskService.findAllAsync().thenApply(ResponseEntity::ok);
    }

    @GetMapping("/")
    ResponseEntity<List<Task>> readAllTasks(Pageable pageable) {
        logger.warn("Custom pageable");
        return ResponseEntity.ok(taskRepository.findAll(pageable).getContent());
    }

    @GetMapping("/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return taskRepository
                .findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(
                taskRepository.findByDone(state)
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateTask(@RequestBody @Valid Task toUpdate, @PathVariable int id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    taskRepository.save(task);
                });
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id).ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }
}
