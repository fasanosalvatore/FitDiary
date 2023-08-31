package it.fitdiary.backend.gestionealimento.controller;

import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GestioneAlimentoControllerIntegrationTest {

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
    void setUp() throws IOException {
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
    public void VisualizzaAlimentoSuccess() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/alimenti/getAlimento?idAlimento=1", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, c.getStatusCode());
    }

    @Test
    public void VisualizzaAlimentoBadRequest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/alimenti/getAlimento?idAlimento=", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, c.getStatusCode());
    }

    @Test
    public void VisualizzaListaAlimentiSuccess() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/alimenti/getAllAlimenti", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, c.getStatusCode());
    }

}
