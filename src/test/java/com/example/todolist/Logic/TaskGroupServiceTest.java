package com.example.todolist.Logic;

import com.example.todolist.Repository.TaskGroupRepository;
import com.example.todolist.Repository.TaskRepository;
import com.example.todolist.model.TaskGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException when undone task exists")
    void toggleGroup_when_UndoneGroupExists_throwsIllegalStateException() {
        //given
        TaskRepository mockTaskRepo = taskRepositoryReturning(true);
        //system under test
        var toTest = new TaskGroupService(null, mockTaskRepo);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(0));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("has undone tasks");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when all task done but no TaskGroup found")
    void toggleGroup_when_noId_isFound_throwsIllegalArgumentException() {
        //given
        TaskRepository mockTaskRepo = taskRepositoryReturning(false);
        //and
        var mockGroupRepo = mock(TaskGroupRepository.class);
        when(mockGroupRepo.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new TaskGroupService(mockGroupRepo, mockTaskRepo);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(anyInt()));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("task with id found and toggled done")
    void toggleGroup_allOk_and_toggledDone() {
        //given
        TaskRepository mockTaskRepo = taskRepositoryReturning(false);
        //and
        var taskGroup = new TaskGroup();
        var beforeToggle = taskGroup.isDone();
        //and
        var mockGroupRepo = mock(TaskGroupRepository.class);
        when(mockGroupRepo.findById(anyInt())).thenReturn(Optional.of(taskGroup));
        //system under test
        var toTest = new TaskGroupService(mockGroupRepo, mockTaskRepo);
        //when
        toTest.toggleGroup(0);
        //then
        assertThat(taskGroup.isDone()).isEqualTo(!beforeToggle);
    }
    private static TaskRepository taskRepositoryReturning(boolean value) {
        var mockTaskRepo = mock(TaskRepository.class);
        when(mockTaskRepo.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(value);
        return mockTaskRepo;
    }
}