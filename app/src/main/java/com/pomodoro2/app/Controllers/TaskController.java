package com.pomodoro2.app.Controllers;

import com.pomodoro2.app.Service.TaskService;
import com.pomodoro2.app.dtos.TaskRequestDTO;
import com.pomodoro2.app.dtos.TaskResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    // 🔥 Get logged-in username from security context
    private String getUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    // ✅ Create Task
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO request){
        String username = getUsername();
        TaskResponseDTO created = taskService.createTask(request, username);
        return ResponseEntity.status(201).body(created);
    }

    // ✅ Get My Tasks
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getMyTasks(){
        String username = getUsername();
        List<TaskResponseDTO> tasksList = taskService.getUserTasks(username);
        return ResponseEntity.ok(tasksList);
    }

    // ✅ Update Task
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequestDTO request){

        String username = getUsername();
        TaskResponseDTO updatedTask = taskService.updateTask(id, request, username);
        return ResponseEntity.ok(updatedTask);
    }

    // ✅ Delete Task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        String username = getUsername();
        taskService.deleteTask(id, username);
        return ResponseEntity.noContent().build();
    }
}