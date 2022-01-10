package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.SchedaAlimentare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedaAlimentareRepository
        extends JpaRepository<SchedaAlimentare, Long> {
    /**
     * @param idProtocollo id del protocollo per
     *                     cui eliminare le schede alimentari
     */
    void deleteAllByProtocolloId(Long idProtocollo);
}
