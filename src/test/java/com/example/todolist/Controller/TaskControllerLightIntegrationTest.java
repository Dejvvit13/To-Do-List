package com.example.todolist.Controller;

import com.example.todolist.Repository.TaskRepository;
import com.example.todolist.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TaskController.class)
class TaskControllerLightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    void httpGet_returnsGivenTask() throws Exception {
        //given
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Task("foo", LocalDateTime.now())));
        //when+then
        mockMvc.perform(get("/tasks/11"))
                .andDo(print())
                .andExpect(content().string(containsString("foo")));
    }

}
