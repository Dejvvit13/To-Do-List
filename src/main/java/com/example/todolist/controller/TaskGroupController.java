package com.example.todolist.controller;

import com.example.todolist.logic.TaskGroupService;
import com.example.todolist.model.Task;
import com.example.todolist.model.projection.GroupReadModel;
import com.example.todolist.model.projection.GroupTaskWriteModel;
import com.example.todolist.model.projection.GroupWriteModel;
import com.example.todolist.repository.TaskGroupRepository;
import com.example.todolist.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);

    private final TaskRepository taskRepository;
    private final TaskGroupService taskGroupService;
    private final TaskGroupRepository repository;

    private static final String TASK_GROUPS_SITE = "taskGroups";


    public TaskGroupController(TaskRepository taskRepository, TaskGroupService taskGroupService, TaskGroupRepository repository) {
        this.taskRepository = taskRepository;
        this.taskGroupService = taskGroupService;
        this.repository = repository;
    }

    @ModelAttribute("taskGroups")
    List<GroupReadModel> readAllTaskGroups() {
        return taskGroupService.readAll();
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showTaskGroups(Model model) {
        model.addAttribute("taskGroup", new GroupWriteModel());
        return TASK_GROUPS_SITE;
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addTaskGroup(@ModelAttribute("taskGroup") @Valid GroupWriteModel current, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return TASK_GROUPS_SITE;
        }
        taskGroupService.createGroup(current);
        model.addAttribute("taskGroup", new GroupWriteModel());
        model.addAttribute("taskGroups", readAllTaskGroups());
        model.addAttribute("message", "Task Group Created");
        return TASK_GROUPS_SITE;
    }

    @PostMapping(params = "addTask", produces = MediaType.TEXT_HTML_VALUE ,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroupTask(@ModelAttribute("taskGroup") GroupWriteModel current) {
        current.getTasks().add(new GroupTaskWriteModel());
        return TASK_GROUPS_SITE;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        GroupReadModel result = taskGroupService.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        System.out.println(repository.findAll());
        return ResponseEntity.ok(taskGroupService.readAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(taskRepository.findAllByGroup_Id(id));
    }

    @Transactional
    @PatchMapping(value = "/{id}")
    @ResponseBody
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
