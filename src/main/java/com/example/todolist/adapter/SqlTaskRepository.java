package com.example.todolist.adapter;

import com.example.todolist.repository.TaskRepository;
import com.example.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "SELECT count(*) > 0 FROM tasks WHERE id = :id")
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    @Override
    List<Task> findAllByGroup_Id(Integer groupId);
}
