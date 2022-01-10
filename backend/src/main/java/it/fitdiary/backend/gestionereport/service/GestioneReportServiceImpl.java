package it.fitdiary.backend.gestionereport.service;

import it.fitdiary.backend.entity.ImmaginiReport;
import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionereport.repository.ImmaginiReportRepository;
import it.fitdiary.backend.gestionereport.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GestioneReportServiceImpl implements GestioneReportService {
    /**
     * Repository del report.
     */
    private final ReportRepository reportRepository;

    /**
     * repository dell'immagini del report.
     */
    private final ImmaginiReportRepository immaginiReportRepository;

    /**
     * @param cliente il cliente di cui voglio visualizzare
     *                lo storico dei report
     * @return lista dei report del cliente
     */
    public List<Report> visualizzazioneStoricoProgressi(final Utente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nullo");
        }
        return reportRepository.findAllByCliente(cliente);
    }

    /**
     * metodo che permette l'inserimento di un report a un cliente.
     *
     * @param report      report inserito dal cliente
     * @param urlImmagini url delle immagini caricate nel report
     * @return report salvato nel database
     */
    @Override
    public Report inserimentoReport(final Report report,
                                    final ArrayList<String> urlImmagini) {
        Report newReport = reportRepository.save(report);
        var listaImmagini = new ArrayList<ImmaginiReport>();
        for (String urlImmagie : urlImmagini) {
            listaImmagini.add(immaginiReportRepository.save(
                    new ImmaginiReport(null, urlImmagie, newReport)));
        }
        newReport.setImmaginiReports(listaImmagini);
        return newReport;
    }
}
