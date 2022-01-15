package it.fitdiary.backend.gestionestimaprogressi.service;

import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.gestionestimaprogressi.adapter.StimaProgressiAdapter;
import it.fitdiary.backend.gestionestimaprogressi.adapter.StimaProgressiAdapterImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GestioneStimaProgressiServiceImpl
        implements GestioneStimaProgressiService {

    /**
     * Adapter per ottenere i risultati dell'agente intelligente.
     */
    private StimaProgressiAdapter stimaProgressiAdapter =
            new StimaProgressiAdapterImpl();

    /**
     * @param report Report in cui inserire la stima della variazione del peso.
     * @return Report modificato
     */
    @Override
    public Report generazioneStimaProgressi(final Report report) {
        return stimaProgressiAdapter.calcolaStimaProgressiReport(report);
    }
}
