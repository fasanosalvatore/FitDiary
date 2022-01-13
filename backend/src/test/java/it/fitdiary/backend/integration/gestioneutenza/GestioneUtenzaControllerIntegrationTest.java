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
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Objects;
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
        //when
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);

        var entity = new HttpEntity<String>(headers);
        //given
        var c = restTemplate.exchange(String.format("http://localhost:%d/api/v1/utenti/profilo",port),HttpMethod.GET,entity,String.class);
        //then
        assertEquals(HttpStatus.SC_OK,c.getStatusCodeValue());
        assertTrue(c.getBody().contains(preparatore.getNome()));
    }

    @Test
    public void visualizzaProfiloUtenteSuccess()
            throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenPreparatore2);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange(
                String.format("http://localhost:%d/api/v1/utenti/4",
                        port), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());
    }

    @Test
    public void visualizzaProfiloUtenteErrorUnauthorized()
            throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenPreparatore);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange(
                String.format("http://localhost:%d/api/v1/utenti/4",
                        port), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, c.getStatusCodeValue());
    }
}


