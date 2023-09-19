package it.fitdiary.backend.gestioneschedaalimentare.repository;

import it.fitdiary.backend.entity.IstanzaAlimento;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IstanzaAlimentoRepository extends JpaRepository<IstanzaAlimento,Long> {
  List<IstanzaAlimento> findAllBySchedaAlimentareAndGiornoDellaSettimana(
      SchedaAlimentare schedaAlimentare,
      @NotNull(message = "Il giorno della settimana non può essere nullo") GIORNO_SETTIMANA giornoDellaSettimana);
}
