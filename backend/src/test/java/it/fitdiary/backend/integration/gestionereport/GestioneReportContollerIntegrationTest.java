package it.fitdiary.backend.integration.gestionereport;

import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("email",email);
        parts.add("password",password);
        var c = restTemplate.postForEntity("http" +
                "://localhost:" + port + "/api" +
                "/v1/utenti/login", parts,Object.class);
        for(String cookie : Objects.requireNonNull(
                c.getHeaders().get(HttpHeaders.SET_COOKIE))){
            if(cookie.contains("accessToken")){
                return cookie + ";refreshToken=test";
            }
        }
        return null;
    }

    @Test
    @Disabled
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
        headers.add("Cookie", tokenCliente);
        HttpEntity<String> entity = new HttpEntity<>(parts.toString(),headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.SC_CREATED, c.getStatusCodeValue());

    }

    @Test
    void visualizzaReportSuccessFromCliente() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenCliente2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports/1", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());
    }

    @Test
    void visualizzaReportSuccessFromPreparatore() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports/1", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());
    }

    @Test
    void visualizzaReportErrorFromCliente() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenCliente);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports/1", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, c.getStatusCodeValue());
    }

    @Test
    void visualizzaReportErrorFromPreparatore() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports/1", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, c.getStatusCodeValue());
    }
    @Test
    void visualizzazioneStoricoProgressiPreparatore() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports?clienteId=4", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());
    }
    @Test
    void visualizzazioneStoricoProgressiCliente() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenCliente2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/reports", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());
    }
}