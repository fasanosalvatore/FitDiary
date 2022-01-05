package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.SchedaAllenamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedaAllenamentoRepository extends
        JpaRepository<SchedaAllenamento, Long> {
}
