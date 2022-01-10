package it.fitdiary.backend.gestionereport.service;

import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionereport.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
}
