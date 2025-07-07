package com.carlos.desafio.repository;

import com.carlos.desafio.entity.TaskEntity;
import com.carlos.desafio.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findByIdAndUsuario(Long id, UserEntity usuario);

    List<TaskEntity> findByUsuario(UserEntity usuario);
}
