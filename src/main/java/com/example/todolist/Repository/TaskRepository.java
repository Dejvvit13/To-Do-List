package com.example.todolist.Repository;

import com.example.todolist.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();

    Page<Task> findAll(Pageable pageable);

    boolean existsById(Integer id);
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    Optional<Task> findById(Integer id);

    Task save(Task entity);

    List<Task> findByDone(boolean isDone);

}
