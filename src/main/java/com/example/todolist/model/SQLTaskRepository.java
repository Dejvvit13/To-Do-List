package com.example.todolist.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SQLTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
}
