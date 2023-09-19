package it.fitdiary.backend.gestioneschedaallenamento.service;

import it.fitdiary.backend.entity.IstanzaEsercizio;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.IstanzaEsercizioDTO;

import java.util.List;

public interface GestioneSchedaAllenamentoService {

    SchedaAllenamento creaSchedaAllenamento(List<IstanzaEsercizioDTO> istanzeEsercizioDto, String name,
                                            Long idPreparatore, Integer frequenza);

    SchedaAllenamento modificaSchedaAllenamento(List<IstanzaEsercizioDTO> istanzeEsercizioDto, String name,
                                               Long idPreparatoreRichiedente, Long idScheda, Integer frequenza);

    List<SchedaAllenamento> getSchedeAllenamentoByPreparaore(Long idPreparatoreRichiedente);

    SchedaAllenamento getSchedeAllenamentoById(Long idScheda);

    List<IstanzaEsercizio> getIstanzeEserciziBySchedaAndGiornoDellaSettimana(SchedaAllenamento schedaAllenamento,
                                                                             int giorno_settimana);

}
