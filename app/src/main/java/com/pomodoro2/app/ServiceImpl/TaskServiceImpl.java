package com.pomodoro2.app.ServiceImpl;

import com.pomodoro2.app.Repositories.TaskRepository;
import com.pomodoro2.app.Repositories.userRepository;
import com.pomodoro2.app.Service.TaskService;
import com.pomodoro2.app.dtos.TaskRequestDTO;
import com.pomodoro2.app.dtos.TaskResponseDTO;
import com.pomodoro2.app.entity.tasks;
import com.pomodoro2.app.entity.users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private userRepository userRepo;
    private TaskRepository taskRepo;

    public TaskServiceImpl(userRepository userRepo, TaskRepository taskRepo){
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    // 🔥 Common mapper (Entity → DTO)
    private TaskResponseDTO mapToDTO(tasks task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }

    // ✅ Create Task
    @Override
    public TaskResponseDTO createTask(TaskRequestDTO request, String username) {

        users existingUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        tasks task = new tasks();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setUser(existingUser);

        tasks saved = taskRepo.save(task);

        return mapToDTO(saved);
    }

    // ✅ Get My Tasks
    @Override
    public List<TaskResponseDTO> getUserTasks(String username) {

        users existingUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return taskRepo.findByUserId(existingUser.getId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ✅ Update Task
    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO request, String username) {

        tasks existing = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("task not found"));

        if (!existing.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized: Task does not belong to user");
        }

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setStatus(request.getStatus());

        return mapToDTO(taskRepo.save(existing));
    }

    // ✅ Delete Task
    @Override
    public void deleteTask(Long id, String username) {

        tasks existing = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("task not found"));

        if (!existing.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized: Task does not belong to user");
        }

        taskRepo.delete(existing);
    }
}