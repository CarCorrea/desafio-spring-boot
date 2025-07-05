package com.carlos.desafio.repository;

import com.carlos.desafio.entity.TaskStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatusEntity, Long> {
}
