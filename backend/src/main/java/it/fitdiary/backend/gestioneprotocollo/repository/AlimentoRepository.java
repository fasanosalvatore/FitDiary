package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    /**
     * @param idSchedaAlimentare id della scheda alimentare
     */
    void deleteAllBySchedaAlimentareId(Long idSchedaAlimentare);
}
