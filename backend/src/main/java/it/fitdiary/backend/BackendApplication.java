package it.fitdiary.backend;

import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.gestioneutenza.repository.RuoloRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    @Bean
    CommandLineRunner run(RuoloRepository ruoloRepository) {
        return args -> {
            ruoloRepository.save(new Ruolo(null, "PREPARATORE", LocalDateTime.now(), LocalDateTime.now()));
            ruoloRepository.save(new Ruolo(null, "CLIENTE", LocalDateTime.now(), LocalDateTime.now()));
            ruoloRepository.save(new Ruolo(null, "ADMIN", LocalDateTime.now(), LocalDateTime.now()));
        };

    }
    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

}
