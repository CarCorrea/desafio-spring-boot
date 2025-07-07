package com.carlos.desafio.controller;

import com.carlos.desafio.entity.TaskEntity;
import com.carlos.desafio.service.TaskService;
import com.nuevospa.api.TareasApi;
import com.nuevospa.model.Task;
import com.nuevospa.model.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TaskController implements TareasApi {

    @Autowired
    private TaskService taskService;

    @Override
    public ResponseEntity<List<Task>> tareasGet(){
        List<TaskEntity> entities = taskService.getAllTasksForCurrentUser();
        List<Task> tasks = entities.stream()
                .map(this::toModel)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<Task> tareasIdGet(Integer id){
        TaskEntity entity = taskService.getTaskById(id.longValue());
                return ResponseEntity.ok(toModel(entity));
    }

    @Override
    public ResponseEntity<Void> tareasPost(Task task){
        TaskEntity entity = toEntity(task);
        taskService.createTask(entity);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Void> tareasIdPut(Integer id, Task task){
        TaskEntity entity = toEntity(task);
        taskService.updateTask(id.longValue(), entity);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> tareasIdDelete(Integer id) {
        taskService.deleteTask(id.longValue());
        return ResponseEntity.noContent().build();
    }

    public Task toModel(TaskEntity entity) {
        Task task = new Task();
        task.setId(entity.getId());
        task.setTitulo(entity.getTitulo());
        task.setDescripcion(entity.getDescripcion());

        TaskStatus statusModel = new TaskStatus();
        statusModel.setId(entity.getEstado().getId());
        statusModel.setNombre(entity.getEstado().getDescripcion());
        task.setEstado(statusModel);


        if (entity.getUsuario() != null) {
            task.setUserId(entity.getUsuario().getId());
        }

        return task;
    }

    private TaskEntity toEntity(Task task) {
        TaskEntity entity = new TaskEntity();

        entity.setTitulo(task.getTitulo());
        entity.setDescripcion(task.getDescripcion());

        return entity;
    }
}
