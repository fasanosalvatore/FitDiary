package it.fitdiary.backend.gestionestimaprogressi.service;

import it.fitdiary.backend.entity.Report;

public interface GestioneStimaProgressiService {
    /**
     *
     * @param report nuovo report
     * @return report con la stima del peso
     */
    Report generazioneStimaProgressi(Report report);
}
