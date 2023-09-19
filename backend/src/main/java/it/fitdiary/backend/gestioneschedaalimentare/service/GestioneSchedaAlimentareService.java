package it.fitdiary.backend.gestioneschedaalimentare.service;

import it.fitdiary.backend.entity.IstanzaAlimento;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.gestioneschedaalimentare.controller.dto.IstanzaAlimentoDTO;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface GestioneSchedaAlimentareService {
  SchedaAlimentare creaSchedaAlimentare(List<IstanzaAlimentoDTO> istanzeAlimento, String name,
                                        Long idPreparatore);

  SchedaAlimentare modificaSchedaAlimentare(List<IstanzaAlimentoDTO> istanzeAlimento, String name, Long idPreparatoreRichiedente, Long idScheda);

  List<SchedaAlimentare> getSchedeAlimentariByPreparaore(Long idPreparatoreRichiedente);

  SchedaAlimentare getSchedeAlimentariById(Long idScheda);

  List<IstanzaAlimento> getAlimentiBySchedaAlimentareAndGiornoDellaSettimana(
      SchedaAlimentare schedaAlimentare,
      @NotNull(message = "Il giorno della settimana non può essere nullo") GIORNO_SETTIMANA giornoDellaSettimana);
}
