package it.fitdiary.backend.unit.gestioneutenza.controller;

import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Customer;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.controller.GestioneUtenzaController;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.service.FitDiaryUserDetails;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {GestioneUtenzaController.class})
@ActiveProfiles("test")
class GestioneUtenzaControllerTest {
    @Autowired
    private GestioneUtenzaController gestioneUtenzaController;

    @MockBean
    private GestioneUtenzaService gestioneUtenzaService;
    @Before
    public void setUp() {}

    @Test
    void registrazioneNewUserReturnCreated() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        String utenteJson = "{\n" +
                "    \"nome\": \"Daniele\",\n" +
                "    \"cognome\": \"De Marco\",\n" +
                "    \"dataNascita\": \"2000-03-03\",\n" +
                "    \"sesso\": \"M\",\n" +
                "    \"email\": \"fabrizio@gmail.com\",\n" +
                "    \"password\": \"Daniele123*\",\n" +
                "    \"confermaPassword\": \"Daniele123*\"\n" +
                "}";
        Utente utente = new Utente(null, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", null, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, null, null, null, null, null, null);
        Utente newUtente = new Utente(1L, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        when(gestioneUtenzaService.registrazione(utente)).thenReturn(newUtente);
        when(mock(Customer.class).getId()).thenReturn("custumerId");
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti/preparatore").content(utenteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void registrazioneNewUserReturnClientError() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        String utenteJson = "{\n" +
                "    \"nome\": \"Daniele\",\n" +
                "    \"cognome\": \"De Marco\",\n" +
                "    \"dataNascita\": \"2000-03-03\",\n" +
                "    \"sesso\": \"M\",\n" +
                "    \"email\": \"fabrizio@gmail.com\",\n" +
                "    \"password\": \"Daniele123*\",\n" +
                "    \"confermaPassword\": \"Daniele123*\"\n" +
                "}";
        Utente utente = new Utente(null, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", null, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, null, null, null, null, null, null);
        Utente newUtente = new Utente(1L, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        when(gestioneUtenzaService.registrazione(utente)).thenThrow(IllegalArgumentException.class);
        when(mock(Customer.class).getId()).thenReturn("customerId");
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti/preparatore").content(utenteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void registrazioneNewUserReturnServerError() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        String utenteJson = "{\n" +
                "    \"nome\": \"Daniele\",\n" +
                "    \"cognome\": \"De Marco\",\n" +
                "    \"dataNascita\": \"2000-03-03\",\n" +
                "    \"sesso\": \"M\",\n" +
                "    \"email\": \"fabrizio@gmail.com\",\n" +
                "    \"password\": \"Daniele123*\",\n" +
                "    \"confermaPassword\": \"Daniele123*\"\n" +
                "}";
        Utente utente = new Utente(null, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", null, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, null, null, null, null, null, null);
        Utente newUtente = new Utente(1L, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        Map<String, Object> params = new HashMap<>();
        params.put("email", newUtente.getEmail());
        params.put("name", newUtente.getNome() + " " + newUtente.getCognome());
        when(gestioneUtenzaService.registrazione(utente)).thenReturn(newUtente);
        MockedStatic<Customer> customer = Mockito.mockStatic(Customer.class);
        customer.when(() -> Customer.create(params)).thenThrow(InvalidRequestException.class);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti/preparatore").content(utenteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }
    @Test
    void registrazioneNewUserReturnErrorPassword() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        String utenteJson = "{\n" +
                "    \"nome\": \"Daniele\",\n" +
                "    \"cognome\": \"De Marco\",\n" +
                "    \"dataNascita\": \"2000-03-03\",\n" +
                "    \"sesso\": \"M\",\n" +
                "    \"email\": \"fabrizio@gmail.com\",\n" +
                "    \"password\": \"Daniele12\",\n" +
                "    \"confermaPassword\": \"Daniele123*\"\n" +
                "}";
        Utente utente = new Utente(null, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", null, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, null, null, null, null, null, null);
        Utente newUtente = new Utente(1L, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        when(gestioneUtenzaService.registrazione(utente)).thenReturn(newUtente);
        when(mock(Customer.class).getId()).thenReturn("custumerId");
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti/preparatore").content(utenteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void iscrizioneClienteSuccess() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);

        String nome = "Fabrizio";
        String cognome = "Vitale";
        String email = "fabrizio@gmail.com";
        String emailPrep = "davide@gmail.com";
        String clienteJson = "{\n" +
                "    \"nome\": \"Fabrizio\",\n" +
                "    \"cognome\": \"Vitale\",\n" +
                "    \"email\": \"fabrizio@gmail.com\"\n" +
                "}";
        Utente preparatore = new Utente(1L, "Davide", "La Gamba", emailPrep
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        Utente newCliente = new Utente(2L, nome, cognome, email
                , "Fabrizio123*", true, LocalDate.parse("2000-03-03"), "M", null, null, null,
                null, preparatore, ruoloPrep, null, null, null,null, null);

        Principal principal = () -> "1";
        when(gestioneUtenzaService.inserisciCliente(Long.parseLong(principal.getName()), cognome, email, principal.getName())).thenReturn(newCliente);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti").principal(() -> "1").content(clienteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void iscrizioneClienteBadRequest() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);

        String nome = "Fabrizio";
        String cognome = "Vitale";
        String email = "fabrizio@gmail.com";
        String emailPrep = "davide@gmail.com";
        String clienteJson = "{\n" +
                "    \"nome\": \"Fabrizio\",\n" +
                "    \"cognome\": \"Vitale\",\n" +
                "    \"email\": \"fabrizio@gmail.com\"\n" +
                "}";
        Utente preparatore = new Utente(1L, "Davide", "La Gamba", emailPrep
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        Utente newCliente = new Utente(2L, nome, cognome, email
                , "Fabrizio123*", true, LocalDate.parse("2000-03-03"), "M", null, null, null,
                null, preparatore, ruoloPrep, null, null, null,null, null);
        Principal principal = () -> "1";
        when(gestioneUtenzaService.inserisciCliente(preparatore.getId(), nome, cognome, email)).thenThrow(IllegalArgumentException.class);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti").principal(() -> "1").content(clienteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void visualizzaProfiloSuccess() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);

        Utente utente = new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);

        Principal principal = () -> "1";
        when(gestioneUtenzaService.getById(Long.parseLong(principal.getName()))).thenReturn(utente);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti/profilo").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzaProfiloBadRequest() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);

        Utente utente = new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);

        Principal principal = () -> "1";
        when(gestioneUtenzaService.getById(Long.parseLong(principal.getName()))).thenThrow(IllegalArgumentException.class);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti/profilo").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }



    @Test
    void modificaDatiPersonali() throws Exception {
        Ruolo r = new Ruolo(2L, "CLIENTE", null,null);
        String clienteJson = "{\n" +
                "    \"nome\": \"Francesca\",\n" +
                "    \"cognome\": \"Di Matteo\",\n" +
                "    \"email\": \"beccadimatteoo@gmail.com\",\n" +
                "    \"password\": \"Becca123*\",\n" +
                "    \"dataNascita\": \"2000-10-30\",\n" +
                "    \"telefono\": \"389485921\",\n" +
                "    \"via\": \"Francesco rinaldo\",\n" +
                "    \"cap\": \"94061\",\n" +
                "    \"citta\": \"Agropoli\"\n" +
                "}";
        Utente utenteNonModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                null, null, null, null, null, null, null, r, null, null, null, null, null);
        Utente utenteModificato = new Utente(1L, "Francesca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null, r, null, null, null, null, null);
        Principal principal = () -> "1";
        when(gestioneUtenzaService.modificaDatiPersonali
         (utenteModificato.getId(), utenteModificato)).thenReturn(utenteModificato);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.put("/api/v1/utenti").principal(principal).content(clienteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().

                is2xxSuccessful());
    }

    @Test
    void refreshTokenSuccess() throws Exception {
        Ruolo ruoloPrep = new Ruolo(2L, "PREPARATORE", null,null);
        Utente utente = new Utente(2L, "Davide", "La Gamba", "giaqui@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti/token/refresh").header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicm9sZXMiOlsiUHJlcGFyYXRvcmUiXSwiaXNzIjoiL2FwaS92MS91dGVudGkvbG9naW4iLCJleHAiOjkyMjMzNzIwMzY4NTQ3NzUsImVtYWlsIjoiZ2lhcXVpQGdtYWlsLmNvbSJ9.WY9dtCdMOipeFtD6Y8ptjSGK4u5ujFbxiOYWeR67bro");
        FitDiaryUserDetails userDetails=new FitDiaryUserDetails(utente.getId().toString(), utente.getPassword(), new ArrayList<>());
        userDetails.setId(utente.getId());
        userDetails.setName(utente.getNome());
        userDetails.setSurname(utente.getCognome());
        userDetails.setGender(utente.getSesso());
        when(gestioneUtenzaService.loadUserByUsername("giaqui@gmail.com")).thenReturn(userDetails);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void refreshTokenFailure() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        Utente utente = new Utente(1L, "Davide", "La Gamba", "giaqui@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti/token/refresh").header("Authorization", "Bearer ");
        FitDiaryUserDetails userDetails=new FitDiaryUserDetails(utente.getId().toString(), utente.getPassword(), new ArrayList<>());
        userDetails.setId(utente.getId());
        userDetails.setName(utente.getNome());
        userDetails.setSurname(utente.getCognome());
        userDetails.setGender(utente.getSesso());
        when(gestioneUtenzaService.loadUserByUsername("giaqui@gmail.com")).thenReturn(userDetails);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void refreshTokenNoToken() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        Utente utente = new Utente(1L, "Davide", "La Gamba", "giaqui@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti/token/refresh").header("Authorization", "");
        FitDiaryUserDetails userDetails=new FitDiaryUserDetails(utente.getId().toString(), utente.getPassword(), new ArrayList<>());
        userDetails.setId(utente.getId());
        userDetails.setName(utente.getNome());
        userDetails.setSurname(utente.getCognome());
        userDetails.setGender(utente.getSesso());
        when(gestioneUtenzaService.loadUserByUsername("giaqui@gmail.com")).thenReturn(userDetails);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void visualizzaProfiloUtenteOnSuccess() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        Ruolo ruoloCliente = new Ruolo(2L, "CLIENTE", null,null);

        Utente preparatore = new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        Utente cliente = new Utente(2L, "Rebecca", "La Gamba", "rebe@gmail.com"
                , "Rebecca123*", true, LocalDate.parse("2000-10" +
                "-03"), "F", null, null, null,
                null, preparatore, ruoloCliente, null, null, null,null, null);

        Principal principal = () -> "1";
        when(gestioneUtenzaService.getById(Long.parseLong(principal.getName()))).thenReturn(preparatore);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(true);
        when(gestioneUtenzaService.getById(cliente.getId())).thenReturn(cliente);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti/2").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    void visualizzaProfiloUtenteFailed() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        Ruolo ruoloCliente = new Ruolo(2L, "CLIENTE", null,null);

        Utente preparatore = new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        Utente cliente = new Utente(2L, "Rebecca", "La Gamba", "rebe@gmail.com"
                , "Rebecca123*", true, LocalDate.parse("2000-10" +
                "-03"), "F", null, null, null,
                null, preparatore, ruoloCliente, null, null, null,null, null);

        Principal principal = () -> "1";
        when(gestioneUtenzaService.getById(Long.parseLong(principal.getName()))).thenReturn(preparatore);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(false);
        when(gestioneUtenzaService.getById(cliente.getId())).thenReturn(cliente);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti/2").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    void visualizzaListaUtentiPreparatore() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        Ruolo ruoloCliente = new Ruolo(2L, "CLIENTE", null,null);
        Ruolo ruoloAdmin = new Ruolo(3L, "ADMIN", null,null);

        Utente preparatore = new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        Utente cliente = new Utente(2L, "Rebecca", "La Gamba", "rebe@gmail.com"
                , "Rebecca123*", true, LocalDate.parse("2000-10" +
                "-03"), "F", null, null, null,
                null, preparatore, ruoloCliente, null, null, null,null, null);
        Utente admin = new Utente(3L, "Salvatore", "Fasano", "toretore@gmail.com"
                , "Tore123*", true, LocalDate.parse("1998-11" +
                "-03"), "M", null, null, null,
                null, null, ruoloAdmin, null, null, null,null, null);
        Principal principal = () -> "1";
        List<Utente> listaUtenti=new ArrayList<>();
        listaUtenti.add(cliente);
       preparatore.setListaClienti(listaUtenti);
        when(gestioneUtenzaService.getById(preparatore.getId())).thenReturn(preparatore);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    void visualizzaListaUtentiAdmin() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        Ruolo ruoloCliente = new Ruolo(2L, "CLIENTE", null,null);
        Ruolo ruoloAdmin = new Ruolo(3L, "ADMIN", null,null);

        Utente preparatore = new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                , "Davide123*", true, LocalDate.parse("2000-03" +
                "-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        Utente cliente = new Utente(2L, "Rebecca", "La Gamba", "rebe@gmail.com"
                , "Rebecca123*", true, LocalDate.parse("2000-10" +
                "-03"), "F", null, null, null,
                null, preparatore, ruoloCliente, null, null, null,null, null);
        Utente admin = new Utente(3L, "Salvatore", "Fasano", "toretore@gmail.com"
                , "Tore123*", true, LocalDate.parse("1998-11" +
                "-03"), "M", null, null, null,
                null, null, ruoloAdmin, null, null, null,null, null);
        Principal principal = () -> "3";
        List<Utente> listaUtenti=new ArrayList<>();
        listaUtenti.add(cliente);
        listaUtenti.add(preparatore);
        listaUtenti.add(admin);

        preparatore.setListaClienti(listaUtenti);
        when(gestioneUtenzaService.getById(admin.getId())).thenReturn(admin);
        when(gestioneUtenzaService.visualizzaListaUtenti()).thenReturn(listaUtenti);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/utenti").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}

