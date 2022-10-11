package com.example.todolist.reports;

import com.example.todolist.model.event.TaskEvent;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@NoArgsConstructor
@Table(name = "task_events")
public class PersistedTaskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;
    int taskId;
    String name;
    LocalDateTime occurrence;

    PersistedTaskEvent(TaskEvent source) {
        this.taskId = source.getTaskId();
        this.name = source.getClass().getSimpleName();
        this.occurrence = LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
    }
}
