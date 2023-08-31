package it.fitdiary.backend.gestioneprotocollo.controller;

import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GestioneProtocolloControllerIntegrationTest {
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
    private File fileSchedaAllenamento;
    private File fileSchedaAlimentare;

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
        fileSchedaAllenamento = Files.writeString(Path.of("schedaAllenamento.csv"),
                "Nome;Serie;Ripetizioni;Recupero;Numero Allenamento;Categoria\n" +
                        "pushup;3;10;1;1;petto\n" +
                        "pushup;3;10;1;1;petto\n" +
                        "pushup;3;10;1;1;petto\n" +
                        "pushup;3;10;1;1;petto\n" +
                        "pushup;3;10;1;2;petto\n" +
                        "pushup;3;10;1;2;petto\n" +
                        "pushup;3;10;1;2;petto\n" +
                        "pushup;3;10;1;2;petto\n" +
                        "pushup;3;10;1;3;petto\n" +
                        "pushup;3;10;1;3;petto\n" +
                        "pushup;3;10;1;3;petto").toFile();
        fileSchedaAlimentare = Files.writeString(Path.of("schedaAlimentare.csv"),
                "Nome;Pasto;Giorno;kcal;grammi\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n").toFile();
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
    void visualizzaStoricoProtocolliCliente() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenCliente2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());

    }

    @Test
    void visualizzaStoricoProtocolliPreparatoreForCliente() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli/?clienteId=4", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());

    }

    @Test
    void visualizzaStoricoProtocolliPreparatore() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenPreparatore2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli/", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());

    }

    @Test
    public void visualizzaProtocolloFromClienteTest_Success(){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", tokenCliente2);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli/1", HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.SC_OK, c.getStatusCodeValue());


    }

    @Test
    public void creazioneProtocolloSuccess() throws Exception{
        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        var schedaAllenamento=new FileInputStream(fileSchedaAllenamento);
        byte[] bSchedaAllenamento=new byte[schedaAllenamento.available()];
        schedaAllenamento.read(bSchedaAllenamento);

        ByteArrayResource schedaAllenamentobytes = new ByteArrayResource(bSchedaAllenamento) {
            @Override
            public String getFilename() {
                return fileSchedaAllenamento.getPath();
            }
        };
        multipartRequest.add("dataScadenza","2023-12-12");
        multipartRequest.add("idCliente", 4);
        multipartRequest.add("schedaAllenamento", schedaAllenamentobytes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", tokenPreparatore2);
        HttpEntity<?> entity = new HttpEntity<>(multipartRequest,headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.SC_CREATED, c.getStatusCodeValue());
    }

    @Test
    public void creazioneProtocolloBadRequest() throws Exception{
        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        var schedaAllenamento=new FileInputStream(fileSchedaAllenamento);
        byte[] bSchedaAllenamento=new byte[schedaAllenamento.available()];
        schedaAllenamento.read(bSchedaAllenamento);
        var schedaAlimentare=new FileInputStream(fileSchedaAlimentare);
        byte[] bSchedaAlimentare=new byte[schedaAllenamento.available()];
        schedaAlimentare.read(bSchedaAlimentare);
        ByteArrayResource schedaAlimentarebytes = new ByteArrayResource(bSchedaAlimentare) {
            @Override
            public String getFilename() {
                return fileSchedaAlimentare.getPath();
            }
        };

        ByteArrayResource schedaAllenamentobytes = new ByteArrayResource(bSchedaAllenamento) {
            @Override
            public String getFilename() {
                return fileSchedaAllenamento.getPath();
            }
        };
        multipartRequest.add("dataScadenza","2022-12-12");
        multipartRequest.add("idCliente", 4);
        multipartRequest.add("schedaAllenamento", schedaAlimentarebytes);
        multipartRequest.add("schedaAlimentare", schedaAllenamentobytes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", tokenPreparatore2);
        HttpEntity<?> entity = new HttpEntity<>(multipartRequest,headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.SC_BAD_REQUEST, c.getStatusCodeValue());
    }

    @Test
    public void creazioneProtocolloUnauthorized() throws Exception{
        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        var schedaAllenamento=new FileInputStream(fileSchedaAllenamento);
        byte[] bSchedaAllenamento=new byte[schedaAllenamento.available()];
        schedaAllenamento.read(bSchedaAllenamento);
        var schedaAlimentare=new FileInputStream(fileSchedaAlimentare);
        byte[] bSchedaAlimentare=new byte[schedaAllenamento.available()];
        schedaAlimentare.read(bSchedaAlimentare);
        ByteArrayResource schedaAlimentarebytes = new ByteArrayResource(bSchedaAlimentare) {
            @Override
            public String getFilename() {
                return fileSchedaAlimentare.getPath();
            }
        };

        ByteArrayResource schedaAllenamentobytes = new ByteArrayResource(bSchedaAllenamento) {
            @Override
            public String getFilename() {
                return fileSchedaAllenamento.getPath();
            }
        };
        multipartRequest.add("dataScadenza","2022-12-12");
        multipartRequest.add("idCliente", 4);
        multipartRequest.add("schedaAllenamento", schedaAllenamentobytes);
        multipartRequest.add("schedaAlimentare", schedaAlimentarebytes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", tokenPreparatore);
        HttpEntity<?> entity = new HttpEntity<>(multipartRequest,headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, c.getStatusCodeValue());
    }


    @Test
    public void modificaProtocolloSuccess() throws Exception{
        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        var schedaAllenamento=new FileInputStream(fileSchedaAllenamento);
        byte[] bSchedaAllenamento=new byte[schedaAllenamento.available()];
        schedaAllenamento.read(bSchedaAllenamento);
        ByteArrayResource schedaAllenamentobytes = new ByteArrayResource(bSchedaAllenamento) {
            @Override
            public String getFilename() {
                return fileSchedaAllenamento.getPath();
            }
        };
        multipartRequest.add("schedaAllenamento", schedaAllenamentobytes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", tokenPreparatore2);
        HttpEntity<?> entity = new HttpEntity<>(multipartRequest,headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli/1", HttpMethod.PUT, entity, String.class);
        assertEquals(HttpStatus.SC_CREATED, c.getStatusCodeValue());
    }

    @Test
    public void modificaProtocolloBadRequest() throws Exception{
        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        var schedaAllenamento=new FileInputStream(fileSchedaAllenamento);
        byte[] bSchedaAllenamento=new byte[schedaAllenamento.available()];
        schedaAllenamento.read(bSchedaAllenamento);
        var schedaAlimentare=new FileInputStream(fileSchedaAlimentare);
        byte[] bSchedaAlimentare=new byte[schedaAllenamento.available()];
        schedaAlimentare.read(bSchedaAlimentare);
        ByteArrayResource schedaAlimentarebytes = new ByteArrayResource(bSchedaAlimentare) {
            @Override
            public String getFilename() {
                return fileSchedaAlimentare.getPath();
            }
        };

        ByteArrayResource schedaAllenamentobytes = new ByteArrayResource(bSchedaAllenamento) {
            @Override
            public String getFilename() {
                return fileSchedaAllenamento.getPath();
            }
        };
        multipartRequest.add("schedaAllenamento", schedaAlimentarebytes);
        multipartRequest.add("schedaAlimentare", schedaAllenamentobytes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", tokenPreparatore2);
        HttpEntity<?> entity = new HttpEntity<>(multipartRequest,headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli/1", HttpMethod.PUT, entity, String.class);
        assertEquals(HttpStatus.SC_BAD_REQUEST, c.getStatusCodeValue());
    }

    @Test
    public void modificaProtocolloUnauthorized() throws Exception{
        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        var schedaAllenamento=new FileInputStream(fileSchedaAllenamento);
        byte[] bSchedaAllenamento=new byte[schedaAllenamento.available()];
        schedaAllenamento.read(bSchedaAllenamento);
        var schedaAlimentare=new FileInputStream(fileSchedaAlimentare);
        byte[] bSchedaAlimentare=new byte[schedaAllenamento.available()];
        schedaAlimentare.read(bSchedaAlimentare);
        ByteArrayResource schedaAlimentarebytes = new ByteArrayResource(bSchedaAlimentare) {
            @Override
            public String getFilename() {
                return fileSchedaAlimentare.getPath();
            }
        };

        ByteArrayResource schedaAllenamentobytes = new ByteArrayResource(bSchedaAllenamento) {
            @Override
            public String getFilename() {
                return fileSchedaAllenamento.getPath();
            }
        };
        multipartRequest.add("schedaAllenamento", schedaAllenamentobytes);
        multipartRequest.add("schedaAlimentare", schedaAlimentarebytes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Cookie", tokenPreparatore);
        HttpEntity<?> entity = new HttpEntity<>(multipartRequest,headers);
        var c = restTemplate.exchange("http" +
                "://localhost:" + port + "/api" +
                "/v1/protocolli/1", HttpMethod.PUT, entity, String.class);
        assertEquals(HttpStatus.SC_UNAUTHORIZED, c.getStatusCodeValue());
    }
}
