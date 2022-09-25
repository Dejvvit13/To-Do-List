package com.example.todolist.Logic;

import com.example.todolist.Repository.TaskGroupRepository;
import com.example.todolist.Repository.TaskRepository;
import com.example.todolist.model.Projection.GroupReadModel;
import com.example.todolist.model.Projection.GroupWriteModel;
import com.example.todolist.model.TaskGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope
public class TaskGroupService {

    private final TaskGroupRepository groupRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskGroupService(TaskGroupRepository groupRepository, TaskRepository taskRepository) {
        this.groupRepository = groupRepository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup result = groupRepository.save(source.toGroup());
        return new GroupReadModel(result);

    }

    public List<GroupReadModel> readAll() {
        return groupRepository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int id) {

        if (taskRepository.existsByDoneIsFalseAndGroup_Id(id)) {
            throw new IllegalStateException("Group has undone tasks. Do all the tasks first");
        }
        TaskGroup result = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task group with given id not found"));
        result.setDone(!result.isDone());

    }
}
