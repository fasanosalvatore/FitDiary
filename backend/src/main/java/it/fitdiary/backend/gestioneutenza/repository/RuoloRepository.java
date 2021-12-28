package it.fitdiary.backend.gestioneutenza.repository;

import it.fitdiary.backend.entity.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Ruolo findByNome(String nome);
}