package com.example.todolist.Adapter;

import com.example.todolist.Repository.ProjectRepository;
import com.example.todolist.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlProjectRepository extends JpaRepository<Project, Integer>, ProjectRepository {

    @Override
    @Query("from Project p join fetch p.steps")
    List<Project> findAll();

}

