package it.fitdiary.backend.gestioneprotocollo.controller;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.service.GestioneProtocolloService;
import it.fitdiary.backend.gestioneprotocollo.service.GestioneProtocolloServiceImpl;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GestioneProtocolloController.class})
@ExtendWith(SpringExtension.class)
public class GestioneProtocolloControllerTest {


    @Autowired
    private GestioneProtocolloController gestioneProtocolloController;

    @MockBean
    private GestioneProtocolloService gestioneProtocolloService;
    @MockBean
    private GestioneUtenzaService gestioneUtenzaService;


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
        when(gestioneProtocolloService.getByIdProtocollo(
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
        when(gestioneProtocolloService.getByIdProtocollo(
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
        when(gestioneProtocolloService.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(true);
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
        when(gestioneProtocolloService.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(true);
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
        when(gestioneProtocolloService.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        when(gestioneUtenzaService.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(false);
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
