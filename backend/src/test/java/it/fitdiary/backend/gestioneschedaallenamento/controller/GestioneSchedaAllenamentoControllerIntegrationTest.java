package it.fitdiary.backend.gestioneschedaallenamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.fitdiary.backend.entity.*;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.CreaSchedaAllenamentoDTO;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.IstanzaEsercizioDTO;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.ModificaSchedaAllenamentoDTO;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GestioneSchedaAllenamentoControllerIntegrationTest {
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

    private CreaSchedaAllenamentoDTO creaSchedaAllenamentoDTO;
    private ModificaSchedaAllenamentoDTO modificaSchedaAllenamentoDTO;
    private SchedaAllenamento schedaAllenamento;
    private List<SchedaAllenamento> schedaAllenamentoPreparatore;
    private CategoriaEsercizio categoriaEsercizio;
    private Esercizio esercizio;
    private List<IstanzaEsercizioDTO> istanzeEsercizioDto;
    private IstanzaEsercizioDTO istanzaEsercizioDTO;
    private List<IstanzaEsercizio> istanzeEsercizio;
    private IstanzaEsercizio istanzaEsercizio;
    private Ruolo ruoloPreparatore;
    private String nome;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws IOException {
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

        categoriaEsercizio = new CategoriaEsercizio(1L,"Pettorali");
        istanzeEsercizioDto = new ArrayList<>();
        istanzeEsercizio = new ArrayList<>();

        schedaAllenamento = new SchedaAllenamento();
        schedaAllenamento.setId(1L);
        schedaAllenamento.setDataCreazione(LocalDateTime.now());
        schedaAllenamento.setDataAggiornamento(LocalDateTime.now());

        istanzaEsercizio = new IstanzaEsercizio();

        esercizio = new Esercizio(1L,"Chest press",
                "EserciziPalestra/Air-Twisting-Crunch_waist.gif",categoriaEsercizio);

        istanzaEsercizioDTO = new IstanzaEsercizioDTO(1,2,3,1,
                "Esercizio gambe",1L);
        istanzaEsercizio.setSchedaAllenamento(schedaAllenamento);
        istanzaEsercizio.setEsercizio(esercizio);
        istanzaEsercizio.setGiornoDellaSettimana(istanzaEsercizioDTO.getGiornoDellaSettimana());
        istanzaEsercizio.setDescrizione(istanzaEsercizioDTO.getDescrizione());
        istanzaEsercizio.setRecupero(istanzaEsercizioDTO.getRecupero());
        istanzaEsercizio.setSerie(istanzaEsercizioDTO.getSerie());
        istanzaEsercizio.setRipetizioni(istanzaEsercizioDTO.getRipetizioni());
        istanzeEsercizio.add(istanzaEsercizio);

        istanzeEsercizioDto.add(istanzaEsercizioDTO);


        nome = "Scheda allenamento femminile";
        schedaAllenamento.setNome(nome);
        schedaAllenamento.setPreparatore(preparatore);
        schedaAllenamento.setFrequenza(3);

        schedaAllenamentoPreparatore = new ArrayList<>();
        schedaAllenamentoPreparatore.add(schedaAllenamento);

        creaSchedaAllenamentoDTO = new CreaSchedaAllenamentoDTO(schedaAllenamento.getNome(),
                istanzeEsercizioDto,schedaAllenamento.getFrequenza());
        modificaSchedaAllenamentoDTO = new ModificaSchedaAllenamentoDTO(schedaAllenamento.getNome(),
                istanzeEsercizioDto,schedaAllenamento.getFrequenza(),schedaAllenamento.getId());
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
    @Order(1)
    public void creaSchedaAllenamentoSuccess() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreaSchedaAllenamentoDTO> entity = new HttpEntity<>(creaSchedaAllenamentoDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaAllenamento/creaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    @Order(2)
    public void creaSchedaAllenamentoBadRequestNullName() throws Exception {

        creaSchedaAllenamentoDTO.setName(null);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreaSchedaAllenamentoDTO> entity = new HttpEntity<>(creaSchedaAllenamentoDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaAllenamento/creaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void creaSchedaAllenamentoBadRequestNullListaIstanzeEsercizi() throws Exception {
        creaSchedaAllenamentoDTO.setIstanzeEsercizi(null);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreaSchedaAllenamentoDTO> entity = new HttpEntity<>(creaSchedaAllenamentoDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaAllenamento/creaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    @Order(4)
    public void modificaSchedaAllenamentoSuccess() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ModificaSchedaAllenamentoDTO> entity = new HttpEntity<>(modificaSchedaAllenamentoDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaAllenamento/modificaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void modificaSchedaAllenamentoBadRequestNullName() throws Exception {

        modificaSchedaAllenamentoDTO.setName(null);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ModificaSchedaAllenamentoDTO> entity = new HttpEntity<>(modificaSchedaAllenamentoDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaAllenamento/modificaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void modificaSchedaAllenamentoBadRequestNullListaIstanzeEsercizi() throws Exception {
        modificaSchedaAllenamentoDTO.setIstanzeEsercizi(null);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ModificaSchedaAllenamentoDTO> entity = new HttpEntity<>(modificaSchedaAllenamentoDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaAllenamento/modificaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @Order(7)
    public void getMySchedeAllenamentoSuccess() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/schedaAllenamento/getMySchedeAllenamento", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, c.getStatusCode());
    }
    @Test
    @Order(8)
    public void getSchedaAllenamentoByIdSuccess() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/schedaAllenamento/getSchedaAllenamentoById?idScheda=1", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, c.getStatusCode());
    }
}
