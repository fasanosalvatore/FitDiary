package it.fitdiary.backend;

import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.gestioneutenza.repository.RuoloRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(RuoloRepository ruoloRepository) {
        return args -> {
            ruoloRepository.save(new Ruolo(null, "PREPARATORE", LocalDateTime.now(), LocalDateTime.now()));
            ruoloRepository.save(new Ruolo(null, "CLIENTE", LocalDateTime.now(), LocalDateTime.now()));
            ruoloRepository.save(new Ruolo(null, "ADMIN", LocalDateTime.now(), LocalDateTime.now()));
        };
    }
}
