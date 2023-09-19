package it.fitdiary.backend.gestioneschedaallenamento.repository;

import it.fitdiary.backend.entity.IstanzaAlimento;
import it.fitdiary.backend.entity.IstanzaEsercizio;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IstanzaEsercizioRepository extends JpaRepository<IstanzaEsercizio,Long> {

  List<IstanzaEsercizio> findAllBySchedaAllenamentoAndGiornoDellaSettimana(
      SchedaAllenamento schedaAllenamento,
       int giornoDellaSettimana);
}
