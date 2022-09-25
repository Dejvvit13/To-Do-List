package com.example.todolist.Repository;

import com.example.todolist.model.TaskGroup;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {
    List<TaskGroup> findAll();

    TaskGroup save(TaskGroup entity);

    Optional<TaskGroup> findById(Integer integer);

    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
}
