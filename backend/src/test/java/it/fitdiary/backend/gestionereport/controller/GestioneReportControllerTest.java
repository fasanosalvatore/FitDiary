package it.fitdiary.backend.gestionereport.controller;

import it.fitdiary.backend.entity.ImmaginiReport;
import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionereport.service.GestioneReportServiceImpl;
import it.fitdiary.backend.gestionestimaprogressi.service.GestioneStimaProgressiServiceImpl;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaServiceImpl;
import it.fitdiary.backend.utility.FileUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GestioneReportController.class})
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class GestioneReportControllerTest {

    @Autowired
    private GestioneReportController gestioneReportController;
    @MockBean
    private GestioneReportServiceImpl gestioneReportService;
    @MockBean
    private GestioneUtenzaServiceImpl gestioneUtenzaService;
    @MockBean
    private GestioneStimaProgressiServiceImpl gestioneStimaProgressiService;
    @MockBean
    private Environment env;
    private Ruolo ruoloCliente;
    private Ruolo ruoloPreparatore;
    private Utente cliente;
    private Utente cliente2;
    private Utente preparatore;
    private Utente preparatore2;
    private List<Utente> listaClienti;
    private List<Report> listaReport;
    private Report report;
    private ImmaginiReport img;
    private ArrayList<ImmaginiReport> immaginiReports;

    @BeforeEach
    void setUp() {
        ruoloCliente = new Ruolo(3L, Ruolo.RUOLOCLIENTE, null, null);
        ruoloPreparatore = new Ruolo(2L, Ruolo.RUOLOPREPARATORE, null, null);
        cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true, null, null, null,
                null, null, null, preparatore, ruoloCliente, null, null, listaReport, null,
                null);
        cliente2 = new Utente(3L, "Roberta", "Carlito",
                "roberta@gmail.com", "Passwprd*", true, null, null, null,
                null, null, null, null, ruoloCliente, null, null, null, null,
                null);
        listaClienti = new ArrayList<Utente>();
        listaClienti.add(cliente);
        preparatore = new Utente(2L, "Mario", "Rossi",
                "mariorossi@gmail.com", "Password123*", true, null, null, null,
                null, null, null, null, ruoloPreparatore, null, listaClienti, null, null,
                null);
        preparatore2 = new Utente(4L, "Tommaso", "Rossi",
                "tomrossi@gmail.com", "Password123*", true, null, null, null,
                null, null, null, null, ruoloPreparatore, null, null, null, null,
                null);
        img = new ImmaginiReport(1l, "img1", null);
        immaginiReports = new ArrayList<>();
        immaginiReports.add(img);
        report = new Report(1l, 80f, 100f, 40f, 40f, 40f, cliente, null, null,
                immaginiReports);
        listaReport = new ArrayList<Report>();
        listaReport.add(report);
    }

    @Test
    void inserisciReportWithFotoMoreSmall_Error() throws Exception {
        var reportNotSave = new Report(null, 80f, 100f, 40f, 40f, 40f, cliente,
                null, null,
                null);
        var urlString=new ArrayList<String>();
        MockMultipartFile multipartFoto = new MockMultipartFile(
                "immagini", null, null,
                new byte[0]);
        Principal principal = () -> "1";
        when(gestioneUtenzaService.getById(1l)).thenReturn(cliente);
        when(gestioneReportService.inserimentoReport(reportNotSave,urlString)).thenReturn(report);
        when(gestioneStimaProgressiService.generazioneStimaProgressi(report)).thenReturn(report);
        when(env.getProperty("cloudinary.url")).thenReturn("cloudinary" +
                "://988346186838798:HkCMSqB99uwY8VaPv5a3y7h6Eiw@hdjxm4zyg");
        var map=new HashMap<String,Object>();
                MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/reports").file(multipartFoto)
                        .param("peso", "80").param("crfBicipite"
                                , "40")
                        .param("crfAddome", "40").param("crfQuadricipite", "40")

                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void inserisciReportWithFoto() throws Exception {
        var reportNotSave = new Report(null, 80f, 100f, 40f, 40f, 40f, cliente,
                null, null,
                null);
        var urlString=new ArrayList<String>();
        Principal principal = () -> "1";
        var foto=new File(
                getClass().getClassLoader().getResource("Schermata-2016-10-27-alle-14.52.19.png")
                        .getFile());
        MockMultipartFile multipartFoto = new MockMultipartFile(
                "immagini", foto.getAbsolutePath(), null,
                new FileInputStream(foto));
        MockedStatic<FileUtility> fileUtility=
                Mockito.mockStatic(FileUtility.class);
        fileUtility.when(()->FileUtility.getFile(multipartFoto)).thenReturn(foto);
        when(gestioneUtenzaService.getById(1l)).thenReturn(cliente);
        when(gestioneReportService.inserimentoReport(reportNotSave,urlString)).thenReturn(report);
        when(gestioneStimaProgressiService.generazioneStimaProgressi(report)).thenReturn(report);
        when(env.getProperty("cloudinary.url")).thenReturn("cloudinary" +
                "://988346186838798:HkCMSqB99uwY8VaPv5a3y7h6Eiw@hdjxm4zyg");
        var map=new HashMap<String,Object>();
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/reports").file(multipartFoto)
                        .param("peso", "80").param("crfBicipite"
                                , "40")
                        .param("crfAddome", "40").param("crfQuadricipite", "40")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzaReportSuccessFromCliente() throws Exception {
        Principal principal = () -> "1";
        long idReport=1L;
        when(gestioneUtenzaService.getById(1l)).thenReturn(cliente);
        when(gestioneReportService.getById(idReport)).thenReturn(report);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzaReportErrorFromCliente() throws Exception {
        Principal principal = () -> "3";
        long idReport=1L;
        when(gestioneUtenzaService.getById(3l)).thenReturn(cliente2);
        when(gestioneReportService.getById(idReport)).thenReturn(report);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void visualizzaReportSuccessFromPreparatore() throws Exception {
        Principal principal = () -> "2";
        long idReport=1L;
        when(gestioneUtenzaService.getById(2l)).thenReturn(preparatore);
        when(gestioneReportService.getById(idReport)).thenReturn(report);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore, report.getCliente().getId())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzaReportErrorFromPreparatore() throws Exception {
        Principal principal = () -> "4";
        long idReport=1L;
        when(gestioneUtenzaService.getById(4l)).thenReturn(preparatore2);
        when(gestioneReportService.getById(idReport)).thenReturn(report);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore2, report.getCliente().getId())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isUnauthorized());
    }
    @Test
    void visualizzazioneStoricoProgressiPreparatore() throws Exception {
        Principal principal = () -> "2";
        when(gestioneUtenzaService.getById(preparatore.getId())).thenReturn(preparatore);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(true);
        when(gestioneReportService.visualizzazioneStoricoProgressi(cliente)).thenReturn(cliente.getListaReport());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports").param("clienteId",
                                String.valueOf(cliente.getId()))
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzazioneStoricoProgressiCliente() throws Exception {
        Principal principal = () -> "1";
        when(gestioneUtenzaService.getById(cliente.getId())).thenReturn(cliente);
        when(gestioneReportService.visualizzazioneStoricoProgressi(cliente)).thenReturn(cliente.getListaReport());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }
}