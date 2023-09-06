package it.fitdiary.backend.gestioneschedaallenamento.service;

import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.IstanzaEsercizioDTO;

import java.util.List;

public interface GestioneSchedaAllenamentoService {

    SchedaAllenamento creaSchedaAllenamento(List<IstanzaEsercizioDTO> istanzeEsercizioDto, String name,
                                            Long idPreparatore, Integer frequenza);

    SchedaAllenamento modificaSchedaAllenamento(List<IstanzaEsercizioDTO> istanzeEsercizioDto, String name,
                                               Long idPreparatoreRichiedente, Long idScheda, Integer frequenza);

    List<SchedaAllenamento> getSchedeAllenamentoByPreparaore(Long idPreparatoreRichiedente);

    SchedaAllenamento getSchedeAllenamentoById(Long idScheda);

}
