package com.example.todolist.model.event;

import com.example.todolist.model.Task;
import lombok.Getter;

import java.time.Clock;
import java.time.Instant;

@Getter
public abstract class TaskEvent {

    public static TaskEvent changed(Task source) {
        return source.isDone() ? new TaskDone(source) : new TaskUndone(source);
    }

    private int taskId;
    private Instant occurrence;

    TaskEvent(int taskId, Clock clock) {
        this.taskId = taskId;
        this.occurrence = Instant.now(clock);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {" +
                "taskId=" + taskId +
                ", occurrence=" + occurrence +
                '}';
    }
}
