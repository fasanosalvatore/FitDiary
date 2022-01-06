package it.fitdiary.backend.gestioneprotocollo.controller;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.service.GestioneProtocolloService;
import it.fitdiary.backend.gestioneprotocollo.service.GestioneProtocolloServiceImpl;
import it.fitdiary.backend.gestioneutenza.controller.GestioneUtenzaController;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GestioneProtocolloController.class})
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class GestioneProtocolloControllerTest {
    @Autowired
    private GestioneProtocolloController gestioneProtocolloController;
    @MockBean
    private GestioneUtenzaServiceImpl gestioneUtenzaServiceImpl;
    @MockBean
    private GestioneProtocolloServiceImpl gestioneProtocolloServiceImpl;
    private Ruolo ruoloCliente;
    private Ruolo ruoloPreparatore;
    private Utente cliente;
    private Utente clienteAggiornato;
    private Utente preparatore;
    private Utente updatedPreparatore;
    private Utente cliente1;
    private Utente preparatore1;
    private Protocollo protocollo;

    @Before
    public void setUp() {
        ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);
        cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true, null, null, null,
                null, null, null, null, ruoloCliente, null, null, null, null,
                null);
        clienteAggiornato = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        updatedPreparatore =
                new Utente(1L, "Michele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true,
                        LocalDate.parse("2000-03-03"), null, "3459666587",
                        "Francesco La Francesca", "84126", "Salerno", null,
                        ruoloPreparatore, null, null, null, null, null);
        protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);

         cliente1 = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);


         preparatore1 =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPreparatore, null, null, null, null, null);

    }

    @Test
    void visualizzaStoricoProtocolliCliente() throws Exception {

        when(gestioneProtocolloServiceImpl.visualizzaStoricoProtocolliCliente(
                cliente)).thenReturn(new ArrayList<Protocollo>());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/protocolli/cliente/1");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(
                        this.gestioneProtocolloController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void visualizzaProtocolloFromClienteTest_Success() throws Exception {

        Ruolo ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);

        Utente cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente preparatore =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPrep, null, null, null, null, null);

        Protocollo protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        Principal principal = () -> "1";
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/protocolli/clienti/1/last")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void visualizzaProtocolloFromClienteTest_BadRequest()
            throws Exception {

        Ruolo ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);

        Utente cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente preparatore =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPrep, null, null, null, null, null);

        Protocollo protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        Principal principal = () -> "5";
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/protocolli/clienti/1/last")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void visualizzaProtocolloFromPreparatoreTest_Success()
            throws Exception {

        Ruolo ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);

        Utente cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente preparatore =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPrep, null, null, null, null, null);

        Protocollo protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        Principal principal = () -> "1";
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/protocolli/preparatore/1/last")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void visualizzaProtocolloFromPreparatoreTest_NotAcceptable()
            throws Exception {

        Ruolo ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);

        Utente cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente preparatore =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPrep, null, null, null, null, null);

        Protocollo protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        Principal principal = () -> "8";
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/protocolli/preparatore/1/last")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isNotAcceptable());

    }

    @Test
    public void visualizzaProtocolloFromPreparatoreTest_BadRequest()
            throws Exception {

        Ruolo ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);

        Utente cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente preparatore =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPrep, null, null, null, null, null);

        Protocollo protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        Principal principal = () -> "1";
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/protocolli/preparatore/1/last")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());

    }
}