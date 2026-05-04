package com.taskmanager.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class TaskManager {

    private ArrayList<Task> tasks;
    private Scanner scanner;
    private final String FILE_NAME = "tasks.txt";

    public TaskManager(Scanner scanner) {
        this.tasks = new ArrayList<>();
        this.scanner = scanner;
        loadFromFile();
    }

    public void addTask() {
        System.out.print("\nEnter task title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();

        String priority = readPriority();
        LocalDate deadline = readDeadline();

        Task task = new Task(title, description, priority, deadline);
        tasks.add(task);
        saveToFile();

        System.out.println("Task added successfully.");
    }

    public void viewAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks found.");
            return;
        }

        System.out.println("\n--- All Tasks ---");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void markTaskAsCompleted() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks found.");
            return;
        }

        viewAllTasks();
        int index = readTaskNumber("\nEnter task number to mark as completed: ");

        if (tasks.get(index).isCompleted()) {
            System.out.println("Task is already completed.");
            return;
        }

        tasks.get(index).markAsCompleted();
        saveToFile();
        System.out.println("Task marked as completed.");
    }

    public void deleteTask() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks found.");
            return;
        }

        viewAllTasks();
        int index = readTaskNumber("\nEnter task number to delete: ");

        Task removedTask = tasks.remove(index);
        saveToFile();
        System.out.println("Task deleted successfully: " + removedTask.getTitle());
    }

    public void searchTaskByTitle() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks found.");
            return;
        }

        System.out.print("\nEnter task title to search: ");
        String searchText = scanner.nextLine().trim().toLowerCase();

        boolean found = false;

        System.out.println("\n--- Search Results ---");
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(searchText)) {
                System.out.println(task);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks found with that title.");
        }
    }

    public void showOverdueTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks found.");
            return;
        }

        boolean found = false;

        System.out.println("\n--- Overdue Tasks ---");
        for (Task task : tasks) {
            if (!task.isCompleted() && task.getDeadline().isBefore(LocalDate.now())) {
                System.out.println(task);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No overdue tasks.");
        }
    }

    public void sortTasksByDeadline() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks found.");
            return;
        }

        tasks.sort(Comparator.comparing(Task::getDeadline));
        saveToFile();

        System.out.println("\nTasks sorted by deadline successfully.");
        viewAllTasks();
    }

    public void sortTasksByPriority() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks found.");
            return;
        }

        tasks.sort((task1, task2) ->
                Integer.compare(getPriorityValue(task2.getPriority()), getPriorityValue(task1.getPriority())));
        saveToFile();

        System.out.println("\nTasks sorted by priority successfully.");
        viewAllTasks();
    }

    public void showCompletedTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks found.");
            return;
        }

        boolean found = false;

        System.out.println("\n--- Completed Tasks ---");
        for (Task task : tasks) {
            if (task.isCompleted()) {
                System.out.println(task);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No completed tasks.");
        }
    }

    public void showPendingTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks found.");
            return;
        }

        boolean found = false;

        System.out.println("\n--- Pending Tasks ---");
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                System.out.println(task);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No pending tasks.");
        }
    }

    public void clearAllTasks() {
        tasks.clear();
        saveToFile();
        System.out.println("\nAll tasks have been cleared.");
    }

    public void exitAndClearAll() {
        tasks.clear();

        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }

        System.out.println("\nAll tasks cleared. Exiting the program.");
    }

    private int getPriorityValue(String priority) {
        switch (priority.toLowerCase()) {
            case "high":
                return 3;
            case "medium":
                return 2;
            case "low":
                return 1;
            default:
                return 0;
        }
    }

    private String readPriority() {
        while (true) {
            System.out.print("Enter priority (high/medium/low): ");
            String priority = scanner.nextLine().trim().toLowerCase();

            if (priority.equals("high") || priority.equals("medium") || priority.equals("low")) {
                return priority;
            }

            System.out.println("Invalid priority. Please enter high, medium, or low.");
        }
    }

    private LocalDate readDeadline() {
        while (true) {
            System.out.print("Enter deadline (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private int readTaskNumber(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                int number = Integer.parseInt(input);

                if (number >= 1 && number <= tasks.size()) {
                    return number - 1;
                } else {
                    System.out.println("Please enter a number between 1 and " + tasks.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(
                        task.getId() + ";" +
                        task.getTitle() + ";" +
                        task.getDescription() + ";" +
                        task.getPriority() + ";" +
                        task.getDeadline() + ";" +
                        task.isCompleted()
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String description = parts[2];
                    String priority = parts[3];
                    LocalDate deadline = LocalDate.parse(parts[4]);
                    boolean completed = Boolean.parseBoolean(parts[5]);

                    Task task = new Task(id, title, description, priority, deadline, completed);
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            // file may not exist on first run
        }
    }

public void showStatistics() {
    int total = tasks.size();
    int completed = 0;
    int pending = 0;
    int overdue = 0;

    LocalDate today = LocalDate.now();

    for (Task task : tasks) {
        if (task.isCompleted()) {
            completed++;
        } else {
            pending++;
            if (task.getDeadline() != null && task.getDeadline().isBefore(today)) {
                overdue++;
            }
        }
    }

    System.out.println("\n===== TASK STATISTICS =====");
    System.out.println("Total Tasks: " + total);
    System.out.println("Completed Tasks: " + completed);
    System.out.println("Pending Tasks: " + pending);
    System.out.println("Overdue Tasks: " + overdue);
}

}