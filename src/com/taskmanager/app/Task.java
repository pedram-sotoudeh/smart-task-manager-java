package com.taskmanager.app;

import java.time.LocalDate;

public class Task {

    private static int counter = 1;

    private int id;
    private String title;
    private String description;
    private String priority;
    private LocalDate deadline;
    private boolean completed;

    // Constructor for new tasks
    public Task(String title, String description, String priority, LocalDate deadline) {
        this.id = counter++;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.completed = false;
    }

    // Constructor for loading tasks from file
    public Task(int id, String title, String description, String priority, LocalDate deadline, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.completed = completed;

        if (id >= counter) {
            counter = id + 1;
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markAsCompleted() {
        completed = true;
    }

    @Override
    public String toString() {
        String status = completed ? "Completed" : "Pending";

        return "ID: " + id +
               " | Title: " + title +
               " | Description: " + description +
               " | Priority: " + priority +
               " | Deadline: " + deadline +
               " | Status: " + status;
    }
}