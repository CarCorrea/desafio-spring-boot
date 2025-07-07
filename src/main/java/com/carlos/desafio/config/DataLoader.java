package com.carlos.desafio.config;

import com.carlos.desafio.entity.TaskStatusEntity;
import com.carlos.desafio.entity.UserEntity;
import com.carlos.desafio.repository.TaskStatusRepository;
import com.carlos.desafio.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               TaskStatusRepository statusRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.save(new UserEntity(null, "usuario1", passwordEncoder.encode("password123")));
                userRepository.save(new UserEntity(null, "usuario2", passwordEncoder.encode("password321")));
            }

            if (statusRepository.count() == 0) {
                statusRepository.save(new TaskStatusEntity(null, "Pendiente"));
                statusRepository.save(new TaskStatusEntity(null, "En progreso"));
                statusRepository.save(new TaskStatusEntity(null, "Completada"));
            }
        };
    }
}
