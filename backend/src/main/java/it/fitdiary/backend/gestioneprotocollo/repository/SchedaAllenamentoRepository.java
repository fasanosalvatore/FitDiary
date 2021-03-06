package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.SchedaAllenamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedaAllenamentoRepository extends
        JpaRepository<SchedaAllenamento, Long> {
     /**
      * @param idProtocollo id del protocollo per
      *      *                     cui eliminare le schede allenamento
      */
     void deleteAllByProtocolloId(Long idProtocollo);
}
