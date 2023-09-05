package it.fitdiary.backend.gestioneschedaalimentare.controller;


import it.fitdiary.backend.entity.*;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.entity.enums.PASTO;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import it.fitdiary.backend.getsioneschedaalimentare.controller.GestioneSchedaAlimentareController;
import it.fitdiary.backend.getsioneschedaalimentare.controller.dto.CreaSchedaAlimentareDTO;
import it.fitdiary.backend.getsioneschedaalimentare.controller.dto.IstanzaAlimentoDTO;
import it.fitdiary.backend.getsioneschedaalimentare.controller.dto.ModificaSchedaDTO;
import it.fitdiary.backend.getsioneschedaalimentare.service.GestioneSchedaAlimentareServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
public class GestioneSchedaAlimentareControllerIntegrationTest {

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

    private List<IstanzaAlimento> alimenti;
    private List<IstanzaAlimentoDTO> alimentiDTO;
    private CreaSchedaAlimentareDTO creaSchedaAlimentareDTO;
    private ModificaSchedaDTO modificaSchedaDTO;
    private Alimento alimento;
    private SchedaAlimentare schedaAlimentare;
    private IstanzaAlimento istanzaAlimento;
    private IstanzaAlimentoDTO istanzaAlimentoDTO;
    private Ruolo ruoloPreparatore;



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
    @Order(1)
    public void creaSchedaAlimentareSuccess() throws Exception {
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore2);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        creaSchedaAlimentareDTO = new CreaSchedaAlimentareDTO(schedaAlimentare.getNome(),alimentiDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreaSchedaAlimentareDTO> entity = new HttpEntity<>(creaSchedaAlimentareDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaalimentare/creaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );
        System.out.println(response);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void creaSchedaAlimentareBadRequestNullName() throws Exception {
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        creaSchedaAlimentareDTO = new CreaSchedaAlimentareDTO(null,alimentiDTO);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreaSchedaAlimentareDTO> entity = new HttpEntity<>(creaSchedaAlimentareDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaalimentare/creaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    public void creaSchedaAlimentareBadRequestNullListaAlimenti() throws Exception {
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        creaSchedaAlimentareDTO = new CreaSchedaAlimentareDTO(schedaAlimentare.getNome(),null);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreaSchedaAlimentareDTO> entity = new HttpEntity<>(creaSchedaAlimentareDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaalimentare/creaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    @Order(4)
    public void modificiaSchedaAlimentareSuccess() throws Exception {
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore2);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        modificaSchedaDTO = new ModificaSchedaDTO(schedaAlimentare.getNome(),alimentiDTO,1L);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<ModificaSchedaDTO> entity = new HttpEntity<>(modificaSchedaDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaalimentare/modificaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void modificiaSchedaAlimentareBadRequestNullName() throws Exception {
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        modificaSchedaDTO = new ModificaSchedaDTO(null,alimentiDTO,1L);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ModificaSchedaDTO> entity = new HttpEntity<>(modificaSchedaDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaalimentare/modificaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void modificiaSchedaAlimentareBadRequestNullListaAlimenti() throws Exception {
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        modificaSchedaDTO = new ModificaSchedaDTO(schedaAlimentare.getNome(),null,1L);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ModificaSchedaDTO> entity = new HttpEntity<>(modificaSchedaDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/schedaalimentare/modificaScheda",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void getMySchedeAlimentariSuccess() throws Exception {
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        modificaSchedaDTO = new ModificaSchedaDTO(schedaAlimentare.getNome(),alimentiDTO,1L);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/schedaalimentare/getMySchedeAlimentari", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, c.getStatusCode());
    }

    @Test
    @Order(8)
    public void getSchedaAlimentareByIdSuccess() throws Exception {
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        modificaSchedaDTO = new ModificaSchedaDTO(null,alimentiDTO,1L);



        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/schedaalimentare/getMySchedeAlimentari?idScheda=1", HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, c.getStatusCode());
    }



}
