package it.fitdiary.backend.gestioneutenza.controller;

import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GestioneUtenzaController.class})
@ExtendWith(SpringExtension.class)
class GestioneUtenzaControllerTest {
    @Autowired
    private GestioneUtenzaController gestioneUtenzaController;

    @MockBean
    private GestioneUtenzaService gestioneUtenzaService;

    @Test
    void registrazioneNewUserReturnCreated() throws Exception {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
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
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03" +
                "-03"), null,
                null, "M", null, null, null,
                null, null, null, null, null, null);
        Utente newUtente = new Utente(1L, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03-03"), null,
                null, "M", null, null, null,
                null, null, ruoloPrep, null, null, null);
        when(gestioneUtenzaService.registrazione(utente)).thenReturn(newUtente);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/utenti/preparatore").content(utenteJson).contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.gestioneUtenzaController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}

