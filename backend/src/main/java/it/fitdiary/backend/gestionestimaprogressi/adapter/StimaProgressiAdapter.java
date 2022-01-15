package it.fitdiary.backend.gestionestimaprogressi.adapter;

import it.fitdiary.backend.entity.Report;

public interface StimaProgressiAdapter {
    /**
     *
     * @param report il nuovo report del cliente
     * @return report con aggiunta peso stimato
     */
    Report calcolaStimaProgressiReport(Report report);
}
