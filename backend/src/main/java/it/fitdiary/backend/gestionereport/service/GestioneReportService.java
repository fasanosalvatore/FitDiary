package it.fitdiary.backend.gestionereport.service;

import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Utente;

import java.util.List;

public interface GestioneReportService {
    List<Report> visualizzazioneStoricoProgressi(Utente cliente);
}
