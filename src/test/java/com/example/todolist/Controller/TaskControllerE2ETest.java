package com.example.todolist.Controller;

import com.example.todolist.Repository.TaskRepository;
import com.example.todolist.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void httpGet_returnsAllTasks() {
        //given
        int initial = taskRepository.findAll().size();
        taskRepository.save(new Task("foo", LocalDateTime.now()));
        taskRepository.save(new Task("bar", LocalDateTime.now()));
        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);
        //then
        assertThat(result).hasSize(initial + 2);
    }
}