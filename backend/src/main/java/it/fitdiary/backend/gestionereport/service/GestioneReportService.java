package it.fitdiary.backend.gestionereport.service;

import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Utente;

import java.time.LocalDateTime;
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

    /**
     * @param idReport id del report da visualizzare
     * @return report con id specificato
     */
    Report getById(Long idReport);

    /**
     * cerca report per data.
     * @param idCliente id cliente
     * @param data data creazione report
     * @return report
     */
    Report search(Long idCliente, LocalDateTime data);
}
