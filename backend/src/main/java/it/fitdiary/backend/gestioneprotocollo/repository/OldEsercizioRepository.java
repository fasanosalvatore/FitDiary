package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.Esercizio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OldEsercizioRepository extends JpaRepository<Esercizio, Long> {
    /**
     * @param idSchedaAllenamento id della scheda di allenamento
     */
    void deleteAllBySchedaAllenamentoId(Long idSchedaAllenamento);
}
