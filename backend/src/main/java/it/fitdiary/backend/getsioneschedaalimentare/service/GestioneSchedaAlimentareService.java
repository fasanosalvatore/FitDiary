package it.fitdiary.backend.getsioneschedaalimentare.service;

import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.getsioneschedaalimentare.controller.dto.IstanzaAlimentoDTO;
import java.util.List;

public interface GestioneSchedaAlimentareService {
  SchedaAlimentare creaSchedaAlimentare(List<IstanzaAlimentoDTO> istanzeAlimento, String name,
                                        Long idPreparatore);

  SchedaAlimentare modificaSchedaAlimentare(List<IstanzaAlimentoDTO> istanzeAlimento, String name, Long idPreparatoreRichiedente, Long idScheda);
}
