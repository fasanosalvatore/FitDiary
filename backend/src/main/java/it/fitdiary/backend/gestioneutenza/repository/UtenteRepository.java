package it.fitdiary.backend.gestioneutenza.repository;

import it.fitdiary.backend.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
}
