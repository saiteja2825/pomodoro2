package com.pomodoro2.app.Service;


import com.pomodoro2.app.dtos.TaskRequestDTO;
import com.pomodoro2.app.dtos.TaskResponseDTO;
import com.pomodoro2.app.entity.tasks;

import java.util.List;
public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO request, String username);

    List<TaskResponseDTO> getUserTasks(String username);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO request, String username);

    void deleteTask(Long id, String username);
}