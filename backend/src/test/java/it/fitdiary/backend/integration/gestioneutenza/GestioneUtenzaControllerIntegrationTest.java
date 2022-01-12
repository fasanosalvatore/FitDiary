package it.fitdiary.backend.integration.gestioneutenza;

import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.RuoloRepository;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GestioneUtenzaControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    private Utente preparatore;
    private String tokenPreparatore;
    private Utente cliente;
    private String tokenCliente;
    private Utente admin;
    private String tokenAdmin;

    @BeforeEach
    void setUp() {
        preparatore = utenteRepository.findByEmail("preparatore@fitdiary.it");
        cliente = utenteRepository.findByEmail("cliente@fitdiary.it");
        admin = utenteRepository.findByEmail("admin@fitdiary.it");
        tokenPreparatore = setUpToken(preparatore.getEmail(), "Password123!");
        tokenCliente = setUpToken(cliente.getEmail(), "Password123!");
        tokenAdmin = setUpToken(admin.getEmail(), "Password123!");
    }

    private String setUpToken(String email, String password) {
        MultiValueMap<String, Object> parts =
                new LinkedMultiValueMap<String, Object>();
        parts.add("email", email);
        parts.add("password", password);
        var c = restTemplate.postForEntity("http" +
                "://localhost:" + port + "/api" +
                "/v1/utenti/login", parts, Object.class);
        var d = (LinkedHashMap) c.getBody();
        return (String) ((LinkedHashMap) ((LinkedHashMap) d.get("data")).get(
                "accessToken")).get("token");
    }

    private static Stream<Arguments> provideStringForIsHello() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("hello", true),
                Arguments.of("hello ", false),
                Arguments.of("ciao", false)
        );
    }

    private Stream<Arguments> provideUtenteAndTokenForUtenteEsistente() {
        return Stream.of(
                Arguments.of(this.preparatore, this.tokenPreparatore),
                Arguments.of("hello", true),
                Arguments.of("hello ", false),
                Arguments.of("ciao", false)
        );
    }


    @ParameterizedTest
    @MethodSource("provideStringForIsHello")
    public void isHello_shouldPassIfStringMatches(String input,
                                                  boolean expected) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenPreparatore);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/utenti/hello", HttpMethod.GET, entity, String.class);
        System.out.println(c);
        assertEquals(expected, input.equals("hello"));
    }

    @Test
    public void utenteEsistente_WhenRetrieveProfile_ReturnValidUser()
            throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenPreparatore);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange(
                String.format("http://localhost:%d/api/v1/utenti/profilo",
                        port), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());
    }


}


