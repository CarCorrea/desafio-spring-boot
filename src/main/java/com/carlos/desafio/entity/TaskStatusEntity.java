package com.carlos.desafio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estado_tarea")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;
}
