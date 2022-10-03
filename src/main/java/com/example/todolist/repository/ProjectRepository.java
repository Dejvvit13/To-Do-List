package com.example.todolist.repository;

import com.example.todolist.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> findAll();

    Optional<Project> findById(int id);

    Project save(Project project);

}
