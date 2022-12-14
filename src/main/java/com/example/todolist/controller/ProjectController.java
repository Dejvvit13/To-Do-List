package com.example.todolist.controller;

import com.example.todolist.logic.ProjectService;
import com.example.todolist.model.Project;
import com.example.todolist.model.ProjectStep;
import com.example.todolist.model.projection.ProjectWriteModel;
import io.micrometer.core.annotation.Timed;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
@PreAuthorize()
public class ProjectController {

    private final ProjectService service;

    private static final String PROJECT_SITE = "projects";

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @ModelAttribute("projects")
    List<Project> getProjects() {
        List<Project> projects = service.readAll();
        projects.forEach(p -> p.setSteps(p.getSteps()
                .stream()
                .sorted(Comparator.comparing(ProjectStep::getDaysToDeadline))
                .collect(Collectors.toCollection(LinkedHashSet::new))));
        return projects;
    }

    @GetMapping
    String showProjects(Model model, Authentication auth, Principal p) {
            ProjectWriteModel project = new ProjectWriteModel();
            model.addAttribute("project", project);
            return PROJECT_SITE;
    }

    @PostMapping
    String addProject(@ModelAttribute("project") @Valid ProjectWriteModel current, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return PROJECT_SITE;
        }
        model.addAttribute("message", "Project Created");
        service.save(current);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("projects", getProjects());
        return PROJECT_SITE;
    }

    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current) {
        current.getSteps().add(new ProjectStep());
        return PROJECT_SITE;
    }

    @PostMapping(params = "removeStep")
    String removeProjectStep(@ModelAttribute("project") ProjectWriteModel current) {
        current.getSteps().remove(current.getSteps().size() - 1);
        return PROJECT_SITE;
    }

    @Timed(value = "project.create.group", histogram = true, percentiles = {0.5, 0.95, 0.99})
    @PostMapping("/{id}")
    String createGroup(@ModelAttribute("project") ProjectWriteModel current,
                       Model model, @PathVariable int id,
                       @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
    ) {
        try {
            service.createGroup(id, deadline);
            model.addAttribute("message", "Added Project");
        } catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("message", "Error while creating project");
        }
        return PROJECT_SITE;
    }

}
