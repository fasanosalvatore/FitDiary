package it.fitdiary.backend.gestionereport.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.ImmaginiReport;
import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionereport.repository.ImmaginiReportRepository;
import it.fitdiary.backend.gestionereport.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BackendApplicationTest.class)
class GestioneReportServiceImplTest {

    @InjectMocks
    private GestioneReportServiceImpl gestioneReportService;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private ImmaginiReportRepository immaginiReportRepository;
    private Ruolo ruoloCliente;
    private Utente cliente;
    private Report report;
    private ImmaginiReport img;
    private ArrayList<ImmaginiReport> immaginiReports;

    @BeforeEach
    void setUp() {
        ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);
        cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true, null, null, null,
                null, null, null, null, ruoloCliente, null, null, null, null,
                null);
        img = new ImmaginiReport(1l,"img1",null);
        immaginiReports=new ArrayList<>();
        immaginiReports.add(img);
        report = new Report(1l, 80f, 41f, 40f, 40f, 40f, cliente, null, null,
                immaginiReports);
    }

    @Test
    void inserimentoReport() {
        var reportNotSave = new Report(null, 80f, 41f, 40f, 40f, 40f, cliente,
                null, null,
                null);
        var urlString=new ArrayList<String>();
        urlString.add("img1");
        var imgReport=new ImmaginiReport(null, "img1",report);
        report.setImmaginiReports(null);
        when(reportRepository.save(reportNotSave)).thenReturn(report);
        when(immaginiReportRepository.save(img)).thenReturn(img);
        report.setImmaginiReports(immaginiReports);
        assertEquals(report,
                gestioneReportService.inserimentoReport(reportNotSave,
                        urlString));
    }
}