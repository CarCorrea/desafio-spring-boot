package com.carlos.desafio.repository;

import com.carlos.desafio.entity.TaskStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatusEntity, Long> {
    Optional<TaskStatusEntity> findByDescripcion(String descripcion);
}
