package it.fitdiary.backend.gestioneschedaallenamento.repository;

import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchedaAllenamentoRepository extends JpaRepository<SchedaAllenamento,Long> {

    List<SchedaAllenamento> findAllByPreparatoreId(Long preparatore_id);

    Optional<SchedaAllenamento> findById(Long id_scheda);
}
