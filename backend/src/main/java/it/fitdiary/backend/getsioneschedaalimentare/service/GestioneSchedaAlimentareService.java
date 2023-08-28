package it.fitdiary.backend.getsioneschedaalimentare.service;

import it.fitdiary.backend.entity.IstanzaAlimento;
import it.fitdiary.backend.entity.SchedaAlimentare;
import java.util.List;

public interface GestioneSchedaAlimentareService {
  SchedaAlimentare creaSchedaAlimentare(List<IstanzaAlimento> istanzeAlimento, String name,
                                        Long idPreparatore);

  SchedaAlimentare modificaSchedaAlimentare(List<IstanzaAlimento> istanzeAlimento, String name, Long idPreparatoreRichiedente, Long idScheda);
}
