package it.fitdiary.backend.gestioneschedaalimentare.repository;

import it.fitdiary.backend.entity.SchedaAlimentare;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedaAlimentareRepository extends JpaRepository<SchedaAlimentare,Long> {

  List<SchedaAlimentare> findAllByPreparatoreId(Long preparatore_id);
}
