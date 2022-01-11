package it.fitdiary.backend.gestionereport.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.fitdiary.backend.entity.ImmaginiReport;
import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionereport.service.GestioneReportServiceImpl;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GestioneReportContoller.class})
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class GestioneReportContollerTest {

    @Autowired
    private GestioneReportContoller gestioneReportContoller;
    @MockBean
    private GestioneReportServiceImpl gestioneReportService;
    @MockBean
    private GestioneUtenzaServiceImpl gestioneUtenzaService;
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
        img = new ImmaginiReport(1l, "img1", null);
        immaginiReports = new ArrayList<>();
        immaginiReports.add(img);
        report = new Report(1l, 80f, 100f, 40f, 40f, 40f, cliente, null, null,
                immaginiReports);
    }

    @Test
    void inserisciReportWithoutFoto() throws Exception {
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
        var map=new HashMap<String,Object>();
                MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/reports").file(multipartFoto)
                        .param("peso", "80").param("crfBicipite"
                                , "40")
                        .param("crfAddome", "40").param("crfQuadricipite", "40")

                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportContoller)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }
}