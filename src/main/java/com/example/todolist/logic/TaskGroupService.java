package com.example.todolist.logic;

import com.example.todolist.model.Project;
import com.example.todolist.repository.TaskGroupRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.model.projection.GroupReadModel;
import com.example.todolist.model.projection.GroupWriteModel;
import com.example.todolist.model.TaskGroup;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class TaskGroupService {

    private final TaskGroupRepository groupRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskGroupService(TaskGroupRepository groupRepository, TaskRepository taskRepository) {
        this.groupRepository = groupRepository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        return createGroup(source, null);
    }

    GroupReadModel createGroup(GroupWriteModel source, final Project project) {
        TaskGroup result = groupRepository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return groupRepository.findAll().stream()
                .map(GroupReadModel::new)
                .toList();
    }

    public void toggleGroup(int id) {

        if (taskRepository.existsByDoneIsFalseAndGroup_Id(id)) {
            throw new IllegalStateException("Group has undone tasks. Do all the tasks first");
        }
        TaskGroup result = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task group with given id not found"));
        result.setDone(!result.isDone());
        groupRepository.save(result);

    }
}
