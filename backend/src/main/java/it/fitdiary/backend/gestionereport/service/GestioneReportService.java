package it.fitdiary.backend.gestionereport.service;

import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Utente;

import java.util.ArrayList;
import java.util.List;

public interface GestioneReportService {
    /**
     * @param cliente Cliente di cui visualizzare lo storico dei progressi
     * @return lista di report del cliente
     */
    List<Report> visualizzazioneStoricoProgressi(Utente cliente);
    /**
     * metodo che permette d'inserire un report a un cliente.
     * @param report report inserito dal cliente
     * @param urlImmagini url delle immagini caricate nel report
     * @return report salvato nel db
     */
    Report inserimentoReport(Report report, ArrayList<String> urlImmagini);
}
