package com.example.todolist.Repository;

import com.example.todolist.model.Project;

import java.util.List;

public interface ProjectRepository {

    List<Project> findAll();

    Project findById(Long id);

    Project save(Project project);

}
