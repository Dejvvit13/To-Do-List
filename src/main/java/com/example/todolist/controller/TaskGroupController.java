package com.example.todolist.controller;

import com.example.todolist.logic.TaskGroupService;
import com.example.todolist.repository.TaskGroupRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.model.projection.GroupReadModel;
import com.example.todolist.model.projection.GroupWriteModel;
import com.example.todolist.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);

    private final TaskRepository taskRepository;
    private final TaskGroupService taskGroupService;
    private final TaskGroupRepository repository;

    public TaskGroupController(TaskRepository taskRepository, TaskGroupService taskGroupService, TaskGroupRepository repository) {
        this.taskRepository = taskRepository;
        this.taskGroupService = taskGroupService;
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        GroupReadModel result = taskGroupService.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        System.out.println(repository.findAll());
        return ResponseEntity.ok(taskGroupService.readAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(taskRepository.findAllByGroup_Id(id));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}
