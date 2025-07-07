package com.carlos.desafio.service;

import com.carlos.desafio.entity.TaskEntity;
import com.carlos.desafio.entity.TaskStatusEntity;
import com.carlos.desafio.entity.UserEntity;
import com.carlos.desafio.repository.TaskRepository;
import com.carlos.desafio.repository.TaskStatusRepository;
import com.carlos.desafio.repository.UserRepository;
import com.nuevospa.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    private UserEntity getCurrentUser(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional
    public void createTask(TaskEntity task){
        UserEntity currentUser = getCurrentUser();
        task.setUsuario(currentUser);

        if(task.getEstado() == null){
            TaskStatusEntity defaultStatus = taskStatusRepository.findByDescripcion("Pendiente")
                    .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
            task.setEstado(defaultStatus);
        }

        taskRepository.save(task);
    }

    public TaskEntity getTaskById(Long id){
        UserEntity currentUser = getCurrentUser();
        return taskRepository.findByIdAndUsuario(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada, acceso denegado"));

    }

    @Transactional
    public void updateTask(Long id, TaskEntity updatedTask) {
        TaskEntity current = getTaskById(id);
        current.setTitulo(updatedTask.getTitulo());
        current.setDescripcion(updatedTask.getDescripcion());

        if (updatedTask.getEstado() != null) {
            TaskStatusEntity status = taskStatusRepository.findByDescripcion(updatedTask.getEstado().getDescripcion())
                    .orElseThrow(() -> new RuntimeException("Estado no válido"));
            System.out.println("Estado recibido para update: " + (updatedTask.getEstado() != null ? updatedTask.getEstado().getDescripcion() : "null"));
            current.setEstado(status);
        }

        taskRepository.save(current);
        taskRepository.flush();
    }

    public void deleteTask(Long id){
        TaskEntity current = getTaskById(id);
        taskRepository.delete(current);
    }

    public List<TaskEntity> getAllTasksForCurrentUser() {
        UserEntity currentUser = getCurrentUser();
        return taskRepository.findByUsuario(currentUser);
    }
}
