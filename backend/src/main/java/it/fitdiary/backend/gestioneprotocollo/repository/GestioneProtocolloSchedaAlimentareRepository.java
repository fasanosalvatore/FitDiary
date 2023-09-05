package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.SchedaAlimentare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestioneProtocolloSchedaAlimentareRepository extends JpaRepository<SchedaAlimentare,Long> {
}
