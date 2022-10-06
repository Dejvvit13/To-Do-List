package com.example.todolist.model.projection;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class GroupReadModel {
    private int id;
    private String description;
    /**
     * Deadline form the latest task in group.
     */
    private LocalDateTime deadline;
    private List<GroupTaskReadModel> tasks;

    public GroupReadModel(TaskGroup source) {
        id = source.getId();
        description = source.getDescription();
        source.getTasks().stream()
                .map(Task::getDeadline)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> deadline = date);
        tasks = source.getTasks().stream()
                .map(GroupTaskReadModel::new)
                .collect(Collectors.toList());
    }

}
