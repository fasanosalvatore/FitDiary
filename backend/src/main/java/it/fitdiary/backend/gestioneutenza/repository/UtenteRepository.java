package it.fitdiary.backend.gestioneutenza.repository;

import it.fitdiary.backend.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    /**
     *
     * @param email
     * @return utente
     */
    Utente findByEmail(String email);

    /**
     *
     * @param email
     * @return vero se esiste, altrimenti false
     */
    boolean existsByEmail(String email);
}
