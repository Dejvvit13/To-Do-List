package com.example.todolist.model.projection;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GroupReadModelTest {

    @Test
    @DisplayName("should create null deadline for group when to task deadlines")
    void constructor_noDeadlines_createsNullDeadline(){
        //given
        var source = new TaskGroup();
        source.setDescription("foo");
        source.setTasks(List.<Task>of(new Task("foo",null)));
        //when
        var result = new GroupReadModel(source);
        //then
        assertThat(result).hasFieldOrPropertyWithValue("deadline", null);
    }

}