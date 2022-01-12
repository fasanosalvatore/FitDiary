package it.fitdiary.backend.integration.gestionereport;


import it.fitdiary.backend.entity.ImmaginiReport;
import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionereport.controller.GestioneReportContoller;
import it.fitdiary.backend.gestionereport.service.GestioneReportServiceImpl;
import it.fitdiary.backend.gestioneutenza.repository.RuoloRepository;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaServiceImpl;
import it.fitdiary.backend.utility.FileUtility;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileInputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GestioneReportContollerIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UtenteRepository utenteRepository;

    private Utente preparatore;
    private Utente preparatore2;
    private String tokenPreparatore;
    private String tokenPreparatore2;
    private Utente cliente;
    private Utente cliente2;
    private String tokenCliente;
    private String tokenCliente2;
    private Utente admin;
    private String tokenAdmin;

    @BeforeEach
    void setUp() {
        preparatore = utenteRepository.findByEmail("preparatore@fitdiary.it");
        preparatore2 = utenteRepository.findByEmail("giaqui@gmail.com");
        cliente = utenteRepository.findByEmail("cliente@fitdiary.it");
        cliente2 = utenteRepository.findByEmail("inapina@libero.it");
        admin = utenteRepository.findByEmail("admin@fitdiary.it");
        tokenPreparatore = setUpToken(preparatore.getEmail(), "Password123!");
        tokenPreparatore2 = setUpToken(preparatore2.getEmail(), "Password123!");
        tokenCliente = setUpToken(cliente.getEmail(), "Password123!");
        tokenCliente2 = setUpToken(cliente2.getEmail(), "Password123!");
        tokenAdmin = setUpToken(admin.getEmail(), "Password123!");
    }

    private String setUpToken(String email, String password) {
        MultiValueMap<String, Object>
                parts = new LinkedMultiValueMap<String, Object>();
        parts.add("email", email);
        parts.add("password", password);
        var c = restTemplate.postForEntity("http" +
                "://localhost:" + port + "/api" +
                "/v1/utenti/login", parts, Object.class);
        var d = (LinkedHashMap) c.getBody();
        return (String) ((LinkedHashMap) ((LinkedHashMap) d.get("data")).get(
                "accessToken")).get("token");
    }

    @Test
    void inserisciReport() throws Exception {
        var parts = new JSONObject();
        var foto = new File(
                getClass().getClassLoader()
                        .getResource("Schermata-2016-10-27-alle-14.52.19.png")
                        .getFile());
        parts.put("immagini",foto );
        parts.put("peso", Collections.singletonList(60f));
        parts.put("crfBicipite", Collections.singletonList(40f));
        parts.put("crfAddome", Collections.singletonList(40f));
        parts.put("crfQuadricipite", Collections.singletonList(40f));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(tokenCliente);
        HttpEntity<String> entity = new HttpEntity<>(parts.toString(),headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.SC_CREATED, c.getStatusCodeValue());

    }

    @Test
    void visualizzaReportSuccessFromCliente() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenCliente2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports/1", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());
    }

    @Test
    void visualizzaReportSuccessFromPreparatore() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenPreparatore2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports/1", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());
    }

    @Test
    void visualizzaReportErrorFromCliente() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenCliente);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports/1", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, c.getStatusCodeValue());
    }

    @Test
    void visualizzaReportErrorFromPreparatore() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenPreparatore);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports/1", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, c.getStatusCodeValue());
    }

  /*
  @Test
    void inserisciReportWithFoto() throws Exception {
        var reportNotSave = new Report(null, 80f, 100f, 40f, 40f, 40f, cliente,
                null, null,
                null);
        var urlString = new ArrayList<String>();
        Principal principal = () -> "1";
        var foto = new File(
                getClass().getClassLoader()
                        .getResource("Schermata-2016-10-27-alle-14.52.19.png")
                        .getFile());
        MockMultipartFile multipartFoto = new MockMultipartFile(
                "immagini", foto.getAbsolutePath(), null,
                new FileInputStream(foto));
        MockedStatic<FileUtility> fileUtility =
                Mockito.mockStatic(FileUtility.class);
        fileUtility.when(() -> FileUtility.getFile(multipartFoto))
                .thenReturn(foto);
        when(gestioneUtenzaService.getById(1l)).thenReturn(cliente);
        when(gestioneReportService.inserimentoReport(reportNotSave,
                urlString)).thenReturn(report);
        var map = new HashMap<String, Object>();
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

    @Test
    void visualizzaReportSuccessFromCliente() throws Exception {
        Principal principal = () -> "1";
        long idReport = 1L;
        when(gestioneUtenzaService.getById(1l)).thenReturn(cliente);
        when(gestioneReportService.getById(idReport)).thenReturn(report);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportContoller)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzaReportErrorFromCliente() throws Exception {
        Principal principal = () -> "3";
        long idReport = 1L;
        when(gestioneUtenzaService.getById(3l)).thenReturn(cliente2);
        when(gestioneReportService.getById(idReport)).thenReturn(report);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportContoller)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void visualizzaReportSuccessFromPreparatore() throws Exception {
        Principal principal = () -> "2";
        long idReport = 1L;
        when(gestioneUtenzaService.getById(2l)).thenReturn(preparatore);
        when(gestioneReportService.getById(idReport)).thenReturn(report);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore,
                report.getCliente().getId())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportContoller)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzaReportErrorFromPreparatore() throws Exception {
        Principal principal = () -> "4";
        long idReport = 1L;
        when(gestioneUtenzaService.getById(4l)).thenReturn(preparatore2);
        when(gestioneReportService.getById(idReport)).thenReturn(report);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore2,
                report.getCliente().getId())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportContoller)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void visualizzazioneStoricoProgressiPreparatore() throws Exception {
        Principal principal = () -> "2";
        when(gestioneUtenzaService.getById(preparatore.getId())).thenReturn(
                preparatore);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore,
                cliente.getId())).thenReturn(true);
        when(gestioneReportService.visualizzazioneStoricoProgressi(
                cliente)).thenReturn(cliente.getListaReport());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports").param("clienteId",
                                String.valueOf(cliente.getId()))
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportContoller)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzazioneStoricoProgressiCliente() throws Exception {
        Principal principal = () -> "1";
        when(gestioneUtenzaService.getById(cliente.getId())).thenReturn(
                cliente);
        when(gestioneReportService.visualizzazioneStoricoProgressi(
                cliente)).thenReturn(cliente.getListaReport());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/reports")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneReportContoller)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }
*/
}