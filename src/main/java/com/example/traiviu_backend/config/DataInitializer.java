package com.example.traiviu_backend.config;

import com.example.traiviu_backend.model.User;
import com.example.traiviu_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        String adminEmail = "admin@traiviu.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .email(adminEmail)
                    .passwordHash(passwordEncoder.encode("admin"))
                    .displayName("Admin")
                    .role("ADMIN")
                    .build();

            userRepository.save(admin);
            System.out.println(">>> Usuario admin creado: " + adminEmail);
        }
    }
}