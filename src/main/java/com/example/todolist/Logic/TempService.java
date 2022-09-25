package com.example.todolist.Logic;

import com.example.todolist.Repository.TaskGroupRepository;
import com.example.todolist.model.Task;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TempService {

    void temp(TaskGroupRepository repository) {
        repository.findAll().stream()
                .flatMap(group -> group.getTasks().stream())
                .map(Task::getDescription)
                .collect(Collectors.toList());
    }

}
